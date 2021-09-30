# Chapter 7.2. 표현의 선택

## 병렬 계산 API 의미
```scala
def unit[A](a: A): Par[A]
def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C]
def fork[A](a: => Par[A]): Par[A]
def lazyUnit[A](a: => A): Par[A] = fork(unit(a))
def run[A](a: Par[A]): A
```

### unit
상수 값을 `병렬계산`으로 승격시킨다

### map2
`병렬계산`을 조합하여 새로운 `병렬계산`을 반환한다

### fork
주어진 인수가 동시적으로 평가될 계산임을 표시.
=> 이게 무슨 말이냐면, 동시적으로 평가할 필요가 있는 `병렬계산`과 그렇지 않은 `병렬계산`을 구분해 준다는 뜻.

### run
병렬계산을 실제로 수행해서, 값 추출

### 의문점
- lazyUnit 의 의미가 헷갈린다.
- 왜 fork 는 Par 를 게으른 인자로 받고 있을까? Par 의 값을 실제로 평가하는 것은 어차피 run 을 통해서 일어나는데, Par 가 게으를 이유가 뭘까?

## Executor Service
실제로 계산을 수행해주는 녀석. API 서명은 다음과 같다.

```scala
class ExecutorService {
  def submit[A](a: Callable[A]): Future[A]
}

trait Callable[A] { def call: A } // 사실상, 그냥 게으르게 A 를 가져오는 역할

trait Future[A] {
  def get: A // 값의 반환
  def get(timeout: Long, unit: TimeUnit): A
  def cancel(eventIfRunning: Boolean): Boolean
  def isDone: Boolean
  def isCancelled: Boolean
}
```

## Executor Service 를 이용해서 run 구현하기
### 병렬 계산의 결과를 어떻게 돌려줄까?
```scala
def run[A](s: ExecutorService)(a: Par[A]): ??? // A or Future[A]
```
계산의 결과 형식으로 A 혹은 Future[A] 가 가능하다. Future[A] 로 했을때의 장점은 무엇이 있을까? get, cancel, isDone 과 같은 메서드를 사용하면, run 을 호출하는 쪽에서 결과와 그에 따른 행동을 제어할 수 있는 부분이 많아진다. (Promise 를 떠올려보자)

### run 을 고려할때, Par 의 형식은 무엇이 되어야 할까?
Par 의 의미는 병렬계산이다. 정확히 병렬계산의 `서술`이다. 
즉, 실제로 병렬계산을 해주지는 않는 것을 말한다. 서술은, 실제로 병렬계산을 해주는 주체(추상화 된)를 이용해서, 병렬계산의 결과를 돌려주는 함수로 표현이 가능하다. 

-> 계산을 해주는 주체가 추상화 될 수 있다는 점이 핵심이다. 

따라서 run 이 해주는 일은, 실제로 계산을 해주는 Executor 를 
Par 에 제공해서 계산이 실제로 이루어지게 하는 역할이다. 

```scala
type Par[A] = ExecutorService => Future[A]

def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)
```
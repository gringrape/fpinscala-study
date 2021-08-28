# 내포된 상태 동작
RNG 값(상태)을 명시적으로 언급하거나 전달하지 않도록 구현.

## nonNegativeLessThan
n 보다 작은 수를 뽑기

### 난수들의 치우침 방지
Int 형식이 가질 수 있는 최대값 Int.MaxValue (이하 M) 가 있고, 자연수 n 이 주어져 있다.
난수 발생기에서 i 라는 값을 뽑고 나서, i 를 n 으로 나눈 나머지(mod)를 구하면 n 보다 작은 수를 임의의수를 뽑을 수 있다.

이때, 만약 M 이 n 으로 나누어 떨어지지 않는다면, 어떤 숫자들은 다른 숫자들 보다 출현하는 빈도가 많아지게 된다. 예를들어, M = 100 이고, n = 9 라고 하자. 이때, M 을 n 으로 나누면 1 이 나머지가 되고, 1 은 다른 수보다 출현빈도가 많아지게 된다. M 에 0 ~ 8 까지의 숫자 '그룹'이 9 개 존재하고, 1이 하나더 있는 상황이다.

출현빈도가 똑같아지게 하기 위해서, i 가 'M 을 n 으로 나눈 몫'보다 큰 경우에는, 다시 i 를 발생시키도록 조절할 수 있다. 몫은 i - mod 로 표현할 수 있고, M 은 n 으로 나누어떨어지지 않으므로, n - 1 을 더하면, 음수가 되게 된다. 이 조건에서 다시 i 를 뽑도록 할 수 있다. 

```scala
//...
if (i + (n - 1) - mod < 0) select_again else result
```

### flatMap
flatMap 은 일반적으로 변환 조합기(=> map)에서 layer 를 하나 줄여준 형식으로 이해할 수 있다. 예를 들어, 다음과 같은 배열 조합은 layer 가 1 인 결과를 반환한다.

```javascript
const array = [1, 2, 3, 4, 5];

array.flatMap((i) => [i, i + 1, i + 2]);
```

상태동작 Rand 의 경우에도, flatMap 을 정의할 수 있다. layer 를 줄인다는 개념으로 생각하면, signature 는 다음과 같이 된다. 

```scala
def flatMap(f: Rand[A])(g: A => Rand[B]): Rand[B]
```

여기에 두 상태 동작을 조합한다는 구현을 추가해 줄 수 있다. 상태동작을 조합하는 이유는, 상태를 전달해주기 위해서 이므로, 상태 전달을 위한 코드를 추가해주면 된다.

```scala
def flatMap(f: Rand[A])(g: A => Rand[B]): Rand[B] = (rng: RNG) => {
    val (value, nextRng) = f(rng)
    g(value)(nextRng)
  }
```

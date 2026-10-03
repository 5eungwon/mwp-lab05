class FourBasicOpt:
    """덧셈, 뺄셈, 나눗셈, 곱셈을 수행합니다."""

    def add(self, x, y):
        return x + y

    def subtract(self, x, y):
        return x - y

    def divide(self, x, y):
        # 테스트 요구사항: 0으로 나누는 경우 0을 반환합니다.
        if y == 0:
            return 0
        return x / y

    def multiply(self, x, y):
        return x * y

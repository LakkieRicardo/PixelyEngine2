package pixely.math

class Vector2f(var x: Float, var y: Float) {

    def this(amount: Float) {
        this(amount, amount)
    }

    def +(that: Vector2f): Vector2f = new Vector2f(this.x + that.x, this.y + that.y)

    def +(that: Int): Vector2f = new Vector2f(this.x + that, this.y + that)

    def -(that: Vector2f): Vector2f = new Vector2f(this.x - that.x, this.y - that.y)

    def -(that: Int): Vector2f = new Vector2f(this.x - that, this.y - that)

    def *(that: Vector2f): Vector2f = new Vector2f(this.x * that.x, this.y * that.y)

    def *(that: Int): Vector2f = new Vector2f(this.x * that, this.y * that)

    def /(that: Vector2f): Vector2f = new Vector2f(this.x / that.x, this.y / that.y)

    def /(that: Int): Vector2f = new Vector2f(this.x / that, this.y / that)

    def %(that: Vector2f): Vector2f = new Vector2f(this.x % that.x, this.y % that.y)

    def %(that: Int): Vector2f = new Vector2f(this.x % that, this.y % that)

    def unary_-(): Vector2f = new Vector2f(-this.x, -this.y)

    def toVector2i(): Vector2i = new Vector2i(this.x.asInstanceOf[Int], this.y.asInstanceOf[Int])

}
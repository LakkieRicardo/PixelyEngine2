package pixely.math

class Vector2i(var x: Int, var y: Int) {

    def this(amount: Int) {
        this(amount, amount)
    }

    def +(that: Vector2i): Vector2i = new Vector2i(this.x + that.x, this.y + that.y)

    def +(that: Int): Vector2i = new Vector2i(this.x + that, this.y + that)

    def -(that: Vector2i): Vector2i = new Vector2i(this.x - that.x, this.y - that.y)

    def -(that: Int): Vector2i = new Vector2i(this.x - that, this.y - that)

    def *(that: Vector2i): Vector2i = new Vector2i(this.x * that.x, this.y * that.y)

    def *(that: Int): Vector2i = new Vector2i(this.x * that, this.y * that)

    def /(that: Vector2i): Vector2i = new Vector2i(this.x / that.x, this.y / that.y)

    def /(that: Int): Vector2i = new Vector2i(this.x / that, this.y / that)

    def %(that: Vector2i): Vector2i = new Vector2i(this.x % that.x, this.y % that.y)

    def %(that: Int): Vector2i = new Vector2i(this.x % that, this.y % that)

    def unary_-(): Vector2i = new Vector2i(-this.x, -this.y)

    def toVector2f(): Vector2f = new Vector2f(this.x.asInstanceOf[Float], this.y.asInstanceOf[Float])

}
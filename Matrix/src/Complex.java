public class Complex {
    private double re;
    private double im;
    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public double getRe() {
        return re;
    }
    public void setRe(double re) {
        this.re = re;
    }
    public void setIm(double im) {
        this.im = im;
    }
    public double getIm() {
        return im;
    }
    public Complex add(Complex other) {
        return new Complex(this.re+other.re, this.im+other.im);
    }
    public Complex sub(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }
    public Complex mul(Complex other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new Complex(newRe, newIm);
    }
    public Complex div(Complex other) {
        double denm = other.re * other.re + other.im * other.im;
        if (denm == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denm;
        double newIm = (this.im * other.re - this.re * other.im) / denm;
        return new Complex(newRe, newIm);
    }
    public double abs() {
        return Math.sqrt(re * re + im * im);
    }
    public boolean isZero() {
        return re == 0 && im == 0;
    }
    @Override
    public String toString() {
        if (im == 0) {
            return String.format("%.2f", re);
        } else if (re == 0) {
            return String.format("%.2f", im);
        } else {
            if (im > 0) {
                return String.format("%.2f+%.2fi", re, im);
            } else {
                return String.format("%.2f%.2fi", re, im);
            }

        }
    }
}

public class Vec3i {
    public int x;
    public int y;
    public int z;

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
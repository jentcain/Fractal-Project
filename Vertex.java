public class Vertex implements Reflectable<Vertex>{
    public int x;
    public int y;
    public Vertex(int m, int n){
	this.x=m;
	this.y=n;
    }

    /* This method maps the vertex to its image under the transformation defined
       by the 2x2 matrix with row vectors (the vertices) v and u, and returns it */
    public Vertex reflect(Vertex v, Vertex u){
	Vertex toReturn = new Vertex(this.x*v.x+this.y*v.y, this.x*u.x+this.y*u.y);
	return toReturn;
    }
}

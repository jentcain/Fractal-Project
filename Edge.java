//*The Edge class represents an edge of a polygon. Each Edge has two vertices

import java.util.ArrayList;

public class Edge implements Reflectable<Edge>{
    public Vertex v1;
    public Vertex v2;
    public Edge(Vertex v, Vertex u){
	this.v1=v;
	this.v2=u;
    }

    /* Map each vertex of the edge to its image under the transformation
 defined by the 2x2 matrix with row vectors (the vertices) v and u. Return the line segment connecting the images of the vertices. */
    public Edge reflect(Vertex v, Vertex u){
	Vertex v1=this.v1;
	Vertex v2=this.v2;
	Edge toReturn=new Edge(v1.reflect(v,u),v2.reflect(v,u));
	return toReturn;
    }
}

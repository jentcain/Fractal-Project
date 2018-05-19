//The Polygon class contains ArrayLists of Vertices and Edges.

import java.util.ArrayList;

public class Polygon implements Reflectable<Polygon>{
    private ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public Polygon(){
	vertices=new ArrayList<Vertex>();
	edges=new ArrayList<Edge>();
    }

    /* Map each edge of the polygon to its image under the transformation defined by the 2x2 matrix with row 
       vectors (the vertices) v and u. Return the polygon defined by the images of the edges. */
    public Polygon reflect(Vertex v, Vertex u){
	Polygon toReturn= new Polygon();
	for (Edge e: edges){
	    toReturn.edges.add(e.reflect(v,u));
	}
	return toReturn;
    }

    /* Reflect the polygon across the x-axis, the y-axis, and both the x- and y-axes
       simultaneously and return the 3 reflected polygons along with the original */
    public ArrayList<Polygon> kaleidoscope(){
	//* These 2 vertices are the two row vectors of the matrix for reflection over the x-axis on the x-y plane
     	Vertex v1=new Vertex(1,0);
       	Vertex v2=new Vertex(0,-1);
	//* These 2 vertices are the two row vectors of the matrix for reflection over the y-axis on the x-y plane
	Vertex u1=new Vertex(-1,0);
	Vertex u2=new Vertex(0,1);
	ArrayList<Polygon> polygons=new ArrayList<Polygon>();
	Polygon p1=this.reflect(v1,v2);
	Polygon p2=this.reflect(u1,u2);
	Polygon p3=p2.reflect(v1,v2);
	polygons.add(this);
	polygons.add(p1);
	polygons.add(p2);
	polygons.add(p3);
	return polygons;
    }

    /* Replace the middle third of each edge of the polygon with a square, thus multiplying
       the total number of edges by 4 and creating a variation on the box fractal. Return the new polygon. */
    public Polygon update(){
	Polygon updated=new Polygon();
	Vertex peak1;
	Vertex peak2;
	for (Edge e: this.edges){
	    double dx=e.v2.x-e.v1.x;
	    double dy=e.v2.y-e.v1.y;
	    double length=Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
	    Vertex oneThird = new Vertex(e.v1.x+(int)dx/3,e.v1.y+(int)dy/3);
	    Vertex twoThirds = new Vertex(e.v1.x+(int)dx*2/3,e.v1.y+(int)dy*2/3);
	    if(dx>0&&dy>0){
		peak1=new Vertex((int)(oneThird.x-1.0/3.0*dy),(int) (oneThird.y+1.0/3.0*dx));
		peak2=new Vertex((int)(twoThirds.x-1.0/3.0*dy),(int) (twoThirds.y+1.0/3.0*dx));
	    }
	    else{
		peak1=new Vertex((int)(oneThird.x-1.0/3.0*dy),(int) (oneThird.y+1.0/3.0*dx));
		peak2=new Vertex((int)(twoThirds.x-1.0/3.0*dy),(int) (twoThirds.y+1.0/3.0*dx));
	    }
	    updated.edges.add(new Edge(e.v1,oneThird));
	    updated.edges.add(new Edge(oneThird,peak1));
	    updated.edges.add(new Edge(peak1, peak2));
	    updated.edges.add(new Edge(peak2,twoThirds));
	    updated.edges.add(new Edge(twoThirds,e.v2));
	}
	return updated;
    }

    /* Define the vertices of the initial polygon, whose number of sides is defined by the user when the DrawFractal program is run. The vertices are (mostly) in the fourth quadrant of the Cartesian plane*/
    public void initialize(int x){
	if (x==3){
	    vertices.add(new Vertex(0,0));
	    vertices.add(new Vertex((int)(250*Math.cos(Math.PI/12)),(int)(-250*Math.sin(Math.PI/12))));
	    vertices.add(new Vertex((int)(250*Math.cos(Math.PI*5/12)),(int)(-250*Math.sin(Math.PI*5/12))));
	}
	if (x==4){
	    vertices.add(new Vertex(0,0));
	    vertices.add(new Vertex(200,0));
	    vertices.add(new Vertex(200,-200));
	    vertices.add(new Vertex(0,-200));
	}
	if (x==5){
	    vertices.add(new Vertex(100,-200));
	    vertices.add(new Vertex(5,-131));
	    vertices.add(new Vertex(41,-19));
	    vertices.add(new Vertex(159,-19));
	    vertices.add(new Vertex(195,-131));			  
	}
	if (x==6){
	    vertices.add(new Vertex(150,-187));
	    vertices.add(new Vertex(50,-187));
	    vertices.add(new Vertex(0,-100));
	    vertices.add(new Vertex(50,-13));
	    vertices.add(new Vertex(150,-13));
	    vertices.add(new Vertex(200,-100));
	}
	if (x==7){
	    vertices.add(new Vertex(100,-200));
	    vertices.add(new Vertex(22,-162));
	    vertices.add(new Vertex(3,-78));
	    vertices.add(new Vertex(57,-10));
	    vertices.add(new Vertex(143,-10));
	    vertices.add(new Vertex(197,-78));
	    vertices.add(new Vertex(178,-162));
	}
	if (x==8){
	    vertices.add(new Vertex(123,-190));
	    vertices.add(new Vertex(27,-190));
	    vertices.add(new Vertex(-40,-123));
	    vertices.add(new Vertex(-40,-27));
	    vertices.add(new Vertex(27,40));
	    vertices.add(new Vertex(123,40));
	    vertices.add(new Vertex(190,-27));
	    vertices.add(new Vertex(190,-123));
	}
	if (x==9){
	    vertices.add(new Vertex(75,-225));
	    vertices.add(new Vertex(-21,-190));
	    vertices.add(new Vertex(-73,-101));
	    vertices.add(new Vertex(-55,0));
	    vertices.add(new Vertex(24,66));
	    vertices.add(new Vertex(126,66));
	    vertices.add(new Vertex(205,0));
	    vertices.add(new Vertex(223,-101));
	    vertices.add(new Vertex(171,-190));
	}
	for (int i=0;i<x-1;i++){
	    edges.add(new Edge(vertices.get(i),vertices.get(i+1)));
	}
	edges.add(new Edge(vertices.get(x-1),vertices.get(0)));		      
    }

}

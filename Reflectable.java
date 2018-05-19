public interface Reflectable<E>{
    /* Map the generic to its image under the transformation defined by the 
matrix with row vectors (the vertices) v and u */
    public E reflect(Vertex v, Vertex u);
}

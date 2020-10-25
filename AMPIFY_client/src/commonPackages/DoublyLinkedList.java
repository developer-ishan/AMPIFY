package AMPIFY_client.src.commonPackages;

public class DoublyLinkedList<File>{
    public class Node{
        public File file;
        public Node next;
        public Node previous;
        Node(File file){
            this.file=file;
        }
    }
    public Node head=new Node(null);
    public void add(File file)
    {
        if(head.file==null)
        {
            head.file=file;
            head.next=head;
            head.previous=head;
        }
        else
        {
            Node newNode=new Node(file);
            newNode.next=head;
            newNode.previous=head.previous;
            newNode.previous.next=newNode;
            head.previous=newNode;
        }
    }
    public void remove(File file)
    {
        Node temp=head;
        while(temp.file!=file)
        {
            temp=temp.next;
        }
        if(head.next==head.previous)
        {
            head=null;
        }
        else {
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous.previous;
            temp = null;
        }
    }
    public boolean isEmpty()
    {
        return head.file==null;
    }

}

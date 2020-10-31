package commonPackages;

public class SubtitleDs<String>{
     class Node{
        String subtitle,startTimeStamp,endTimeStamp;
        Node next,previous;
        Node(String subtitle){
            this.subtitle=subtitle;
        }
    }
    Node head=new Node(null);
    public void add(String subtitle,String start,String end){
        Node newNode=new Node(subtitle);
        Node temp=head;
        if(temp.subtitle==null){
            head.endTimeStamp=end;
            head.startTimeStamp=start;
            head.next=null;
            head.previous=null;
            head.subtitle=subtitle;
        }
        else{
            while(temp.next!=null){
                temp=temp.next;
            }
            newNode.next=null;
            newNode.previous=temp;
            newNode.startTimeStamp=start;
            newNode.endTimeStamp=end;
            temp.next=newNode;
        }
    }
    public String search(String timstamp){
        Node temp=head;
        while(true){
            if(temp.startTimeStamp.toString().compareTo(timstamp.toString())>=0&&
            temp.endTimeStamp.toString().compareTo(timstamp.toString())<0){
                return temp.subtitle;
            }
            else {
                temp=temp.next;
            }
        }
    }
}
package com.Nikhil;

/**
 * Created by Nikhil on 3/17/16.
 */
public class RBEventTree {

    private Node root;
    private static Node extNullNode=new Node();


    //Red-Black event counter constructor
    public RBEventTree(){
        root = new Node();
        extNullNode.color = Color.BLACK;
        root.theID = Integer.MIN_VALUE;
        root.left = extNullNode;
        root.right = extNullNode;
        root.parent = extNullNode;
    }


    public void intializer(int[] idArray, int[] countArray){

        int height = (int)Math.log(idArray.length);
        //System.out.println("++"+height);

        if(idArray.length!=0){
            root = buildRBTree(idArray,countArray,0 ,idArray.length - 1, 1, height, extNullNode);
        }


    }

    private Node buildRBTree(int[] idArray, int[] countArray, int start, int end, int currHeight , int maxHeight, Node parent)
    {
        if (start > end)
            return extNullNode;

        int mid = (start + end) / 2;
        Node root = new Node();
        root.theID = idArray[mid];
        root.count = countArray[mid];
        root.color = currHeight!=maxHeight ? Color.BLACK : Color.RED;
        root.parent = parent;
        root.left = buildRBTree(idArray,countArray, start, mid - 1,currHeight+1, maxHeight, root);
        root.right = buildRBTree(idArray,countArray, mid + 1, end, currHeight+1,maxHeight,root);

        return root;
    }
    public void increase(int theID, int m){
        Node node = search(theID);
        if(node.theID == theID)
        {
            node.count = node.count + m;
            System.out.println(node.count);

        }else{
            this.add(theID,m);
            System.out.println(m);
        }

    }

    public void reduce(int theID, int m){
        Node node = search(theID);
        if(node.theID == theID)
        {
            node.count = node.count - m;
            if(node.count<=0)
            {
                delete(node);
                System.out.println("0");
            }else{
                System.out.println(node.count);
            }


        }else
        {
            System.out.println("0");
        }

    }

    public void count(int theID){
        Node node = search(theID);
        if(node.theID == theID)
        {
            System.out.println(node.count);

        }else{

            System.out.println("0");
        }

    }

    public void next(int theID){
        Node nextNode = findNext(theID);


            if(nextNode != null)
            {
                System.out.println(nextNode.theID+" "+nextNode.count);
            }else
            {
                System.out.println("0 0");
            }


    }

    public Node findNext(int theID){
        Node curr = root;
        Node next = null;
        int minDistance = Integer.MAX_VALUE;
        while(curr != extNullNode)
        {
            int distance = curr.theID - theID;
            if(distance < minDistance && distance > 0)
            {
                minDistance = distance;
                next = curr;

            }
            if(curr.theID > theID)
                curr = curr.left;
            else if(curr.theID <= theID)
                curr = curr.right;

        }
        return next;

    }

    public Node findPrev(int theID){
        Node curr = root;
        Node next = null;
        int minDistance = Integer.MAX_VALUE;
        while(curr != extNullNode)
        {
            int distance = theID - curr.theID;
            if(distance < minDistance && distance > 0)
            {
                minDistance = distance;
                next = curr;

            }
            if(curr.theID >= theID)
                curr = curr.left;
            else if(curr.theID < theID)
                curr = curr.right;

        }
        return next;

    }

    public void previous(int theID){
        Node prevNode = findPrev(theID);


        if(prevNode != null)
        {
            System.out.println(prevNode.theID+" "+prevNode.count);
        }else
        {
            System.out.println("0 0");
        }


    }

    public void inrange(int ID1, int ID2){
     int[] array = new int[1];
        inrangeCalculator(root, ID1, ID2, array);
        System.out.println(array[0]);
    }
    public void inrangeCalculator(Node node,int ID1, int ID2, int[] count){

        if (node == extNullNode) {
            return;
        }

        if (ID1 < node.theID) {
            inrangeCalculator(node.left, ID1, ID2, count);
        }

        if (ID1 <= node.theID && ID2 >= node.theID) {
            count[0] = count[0] + node.count;
        }

        if (ID2 > node.theID) {
            inrangeCalculator(node.right, ID1, ID2, count);
        }

    }

    //add a new node to the tree
    public void add(int theID, int count){
        Node currentNode = root;
        Node parentNode = extNullNode;

        // add new Node to the bottom of the tree
        while(!currentNode.equals(extNullNode))
        {
            parentNode = currentNode;
            if(currentNode.theID >= theID)
            {
                currentNode = currentNode.left;

            }else
            {
                if(currentNode.theID == Integer.MIN_VALUE)
                    parentNode = extNullNode;
                currentNode = currentNode.right;
            }


        }

        currentNode = new Node();
        currentNode.parent = parentNode;
        currentNode.theID = theID;
        currentNode.count = count;
        currentNode.color = Color.RED;
        currentNode.left = extNullNode;
        currentNode.right = extNullNode;

        if(parentNode.equals(extNullNode))
        {
            root = currentNode;
        }else if(theID < parentNode.theID)
        {
            parentNode.left = currentNode;
        }else
        {
            parentNode.right = currentNode;
        }


        balanceNewEventInsert(currentNode);

    }


    private void balanceNewEventInsert(Node node)
    {

        while (!node.parent.color.equals(Color.BLACK))
        {
            if(node.parent.equals(node.parent.parent.left))
            {
                Node uncle = node.parent.parent.right;
                if(uncle.color.equals(Color.RED))
                {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                }else
                {
                    if(node.equals(node.parent.right))
                    {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }

            }else {
                Node uncle = node.parent.parent.left;
                if(uncle.color.equals(Color.RED))
                {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                }else
                {
                    if(node.equals(node.parent.left))
                    {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }

        }

        root.color = Color.BLACK;

    }

    private void leftRotate(Node node){
        Node rightNode=node.right;
        node.right = rightNode.left;
        if(!rightNode.left.equals(extNullNode))
        {
            rightNode.left.parent = node;
        }

        rightNode.parent = node.parent;
        if(node.parent.equals(extNullNode))
        {
            root=rightNode;
        }
        else if(node.equals(node.parent.left))
        {
            node.parent.left = rightNode;
        }
        else node.parent.right = rightNode;

        rightNode.left = node;
        node.parent = rightNode;

    }


    private void rightRotate(Node node){
        Node leftNode=node.left;
        node.left = leftNode.right;
        if(!leftNode.right.equals(extNullNode))
        {
            leftNode.right.parent = node;
        }

        leftNode.parent = node.parent;
        if(node.parent.equals(extNullNode))
        {
            root=leftNode;
        }
        else if(node.equals(node.parent.right))
        {
            node.parent.right = leftNode;
        }
        else
        {
            node.parent.left = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;

    }

    private Node search(int theID){
        Node curr=root;
        if(curr.theID > Integer.MIN_VALUE){
            while(curr.theID != theID && !curr.equals(extNullNode)){
                if(curr.theID > theID) {
                    curr = curr.left;
                }
                else
                {
                    curr = curr.right;
                }
            }

        }
        return curr;

    }

    private Node successorOf(Node node){
        Node successor = null;
        if(node != extNullNode)
        {
            if(node.right != extNullNode)
            {
                successor = getMin(node.right);
            }
            else
            {
                successor = node.parent;
                while(successor != extNullNode && successor.right != extNullNode && successor.right.theID == node.theID)
                {
                    node = successor;
                    successor = successor.parent;

                }

            }

        }
        return successor;

    }

    private Node getMin(Node node){
        Node min = node;
        if(min != extNullNode)
        {
            while(min.left != extNullNode)
            {
                min = min.left;
            }

        }
        return min;

    }

    private Node getMax(Node node){
        Node max = node;
        if(max != extNullNode)
        {
            while(max.right != extNullNode)
            {
                max = max.right;
            }

        }
        return max;
    }

    private Node predecessorOf(Node node){
        Node predecessor = null;
        if(node != extNullNode)
        {
            if(node.left != extNullNode)
            {
                predecessor = getMax(node.left);
            }
            else
            {
                predecessor = node.parent;
                while(predecessor != extNullNode && predecessor.left != extNullNode && predecessor.left.theID == node.theID)
                {
                    node = predecessor;
                    predecessor = predecessor.parent;

                }

            }

        }
        return predecessor;

    }


    private void delete(Node node){
        if(node != extNullNode)
        {
            Node org = node;
            Color org_color = org.color;

            Node temp = new Node();
            if(node.left == extNullNode)
            {
                temp = node.right;
                RB_transplant(node,node.right);
            }else if(node.right == extNullNode)
            {
                temp = node.left;
                RB_transplant(node,node.left);
            }else
            {
                org = getMin(node.right);
                org_color = org.color;
                temp = org.right;
                if(org.parent == node)
                {
                    temp.parent = org;
                }
                else
                {
                    RB_transplant(org,org.right);
                    org.right = node.right;
                    node.right.parent = org;
                }
                RB_transplant(node,org);
                org.left = node.left;
                org.left.parent= org;
                org.color = node.color;
            }
            if(org_color == Color.BLACK)
            {
                balanceEventDelete(temp);
            }


        }


    }

    private void RB_transplant(Node u, Node v){
        if(u.parent == extNullNode)
        {
            root = v;
        }
        else if(u == u.parent.left)
        {
            u = u.parent.left;
        }else
        {
            u.parent.right = v;
        }
        v.parent = u.parent;

    }

    private void balanceEventDelete(Node node) {
        while(node != root && node.color == Color.BLACK){
            if(node == node.parent.left)
            {
                Node rightSibling = node.parent.right;
                if(rightSibling.color == Color.RED)
                {
                    rightSibling.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    leftRotate(node.parent);
                    rightSibling = node.parent.right;

                }
                if(rightSibling.left.color == Color.BLACK && rightSibling.right.color == Color.BLACK)
                {
                    rightSibling.color = Color.RED;
                    node = node.parent;
                }else
                {
                    if(rightSibling.right.color == Color.BLACK)
                    {
                        rightSibling.left.color = Color.BLACK;
                        rightSibling.color = Color.RED;
                        rightRotate(rightSibling);
                        rightSibling=node.parent.right;
                    }
                    rightSibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    rightSibling.right.color = Color.BLACK;
                    leftRotate(node.parent);
                    node=root;
                }

            }else
            {
                Node leftSibling = node.parent.left;
                if(leftSibling.color == Color.RED)
                {
                    leftSibling.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    rightRotate(node.parent);
                    leftSibling = node.parent.left;

                }
                if(leftSibling.right.color == Color.BLACK && leftSibling.left.color == Color.BLACK)
                {
                    leftSibling.color = Color.RED;
                    node = node.parent;
                }else
                {
                    if(leftSibling.left.color == Color.BLACK)
                    {
                        leftSibling.right.color = Color.BLACK;
                        leftSibling.color = Color.RED;
                        leftRotate(leftSibling);
                        leftSibling=node.parent.left;
                    }
                    leftSibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    leftSibling.left.color = Color.BLACK;
                    rightRotate(node.parent);
                    node=root;
                }

            }
            node.color = Color.BLACK;


        }




        }







}

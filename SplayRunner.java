
/*
 * Abhi Reddy
 * Project 1
 * CS3345.002
 */
import java.util.Scanner;

//class for the splaytree node
class SNode {
  SNode left, right, parent;
  int data;

  // default Constructs the initial empty splay tree with no parameters
  public SNode() {
    this.parent = null;
    this.left = null;
    this.right = null;
    this.data = 0;
  }

  // Constructor with data paramter for additional nodes to be added to splay tree
  public SNode(int dat) {
    this.parent = null;
    this.left = null;
    this.right = null;
    this.data = dat;
  }

  // constructor with all paramaters
  public SNode(int dat, SNode left, SNode right, SNode parent) {
    this.parent = parent;
    this.left = left;
    this.right = right;
    this.data = dat;
  }
}

// class for the splay tree object
class SplayTree {
  private SNode root;

  // constructs the empty splay tree
  public SplayTree() {
    root = null;
  }

  // finds node then splays it
  public boolean search(int val) {
    return findNode(val) != null;
  }

  private SNode findNode(int dat) {
    SNode PrevNode = null;
    SNode z = root;
    while (z != null) {
      PrevNode = z;
      if (dat > z.data)
        z = z.right;
      else if (dat < z.data)
        z = z.left;
      else if (dat == z.data) {
        Splay(z);
        return z;
      }
    }
    if (PrevNode != null) {
      Splay(PrevNode);
      return null;
    }
    return null;
  }

  // function to insert data
  public void insert(int dat) {
    SNode z = root;
    SNode p = null;
    while (z != null) {
      p = z;
      // if node already exists in tree, that node is splayed
      if (dat == p.data) {
        Splay(p);
        return;
      }
      // traverses to where the node should be inserted
      if (dat > p.data)
        z = z.right;
      else
        z = z.left;
    }
    z = new SNode();
    z.data = dat;
    z.parent = p;
    if (p == null)
      root = z;
    else if (dat > p.data) {
      p.right = z;
    } else {
      p.left = z;
    }
    Splay(z);
  }

  // finds node to delete
  public void delete(int dat) {
    SNode node = findNode(dat);
    delete(node);
  }

  // searches for node to splay, then deletes it if it exists
  private void delete(SNode node) {
    if (node == null)
      return;
    Splay(node);
    if ((node.left != null) && (node.right != null)) {
      SNode min = node.left;
      while (min.right != null)
        min = min.right;
      min.right = node.right;
      node.right.parent = min;
      node.left.parent = null;
      root = node.left;
    } else if (node.right != null) {
      node.right.parent = null;
      root = node.right;
    } else if (node.left != null) {
      node.left.parent = null;
      root = node.left;
    } else {
      root = null;
    }
    node.parent = null;
    node.left = null;
    node.right = null;
    node = null;
  }

  // swaps left child with its parent
  public void swapLeftParent(SNode child, SNode p) {
    if (p.parent != null) {
      if (p == p.parent.left)
        p.parent.left = child;
      else
        p.parent.right = child;
    }
    if (child.right != null)
      child.right.parent = p;
    child.parent = p.parent;
    p.parent = child;
    p.left = child.right;
    child.right = p;
  }

  // swaps right child with its parent
  public void swapRightParent(SNode child, SNode p) {
    if (p.parent != null) {
      if (p == p.parent.left)
        p.parent.left = child;
      else
        p.parent.right = child;
    }
    if (child.left != null)
      child.left.parent = p;
    child.parent = p.parent;
    p.parent = child;
    p.right = child.left;
    child.left = p;
  }

  // splays node to root
  private void Splay(SNode n) {
    while (n.parent != null) {
      SNode Parent = n.parent;
      SNode GParent = Parent.parent;
      if (GParent == null) {
        if (n == Parent.left)
          swapLeftParent(n, Parent);
        else
          swapRightParent(n, Parent);
      } else {
        if (n == Parent.left) {
          if (Parent == GParent.left) {
            swapLeftParent(Parent, GParent);
            swapLeftParent(n, Parent);
          } else {
            swapLeftParent(n, n.parent);
            swapRightParent(n, n.parent);
          }
        } else {
          if (Parent == GParent.left) {
            swapRightParent(n, n.parent);
            swapLeftParent(n, n.parent);
          } else {
            swapRightParent(Parent, GParent);
            swapRightParent(n, Parent);
          }
        }
      }
    }
    root = n;
  }

  // Prints the tree in preorder traversal
  public void display() {
    display(root);
  }

  private void display(SNode n) {
    if (n != null) {
      System.out.print(n.data);
      if (n.data == root.data)
        System.out.print("RT ");
      else if (n.data > n.parent.data)
        System.out.print("R ");
      else if (n.data < n.parent.data)
        System.out.print("L ");
      display(n.left);
      display(n.right);
    }
  }
}

public class SplayRunner {
  public static void main(String[] args) {
    char in;
    Scanner kb = new Scanner(System.in);

    SplayTree spt = new SplayTree();
    System.out.print("\nEnter n: ");
    int N = kb.nextInt();
    for (int i = 1; i <= N; i++) {
      spt.insert(i);
    }
    spt.display();
    // while loop for operations until user enters something that isn't "y"
    do {
      System.out.println("\n1: Search ");
      System.out.println("2: Insert ");
      System.out.println("3: Delete ");
      int choice = kb.nextInt();
      switch (choice) {
        case 1:
          System.out.println("Integer to search: ");
          if (spt.search(kb.nextInt()) == false)
            System.out.println("Integer not found!");
          else
            System.out.println("Integer found!");
          break;
        case 2:
          System.out.println("Integer to insert: ");
          spt.insert(kb.nextInt());
          break;
        case 3:
          System.out.println("Integer to delete: ");
          spt.delete(kb.nextInt());
          break;
        default:
          System.out.println("Please enter 1,2,or 3 \n ");
          break;
      } // Outputs tree in preorder traversal
      System.out.print("\nPre order traversal is: ");
      spt.display();
      System.out.println("\nContinue?(y/n)");
      in = kb.next().charAt(0);
    } while (in == 'y');
    kb.close();
  }
}
package test;

import java.util.ArrayDeque;

public class javaCollectionPrac {
  public static void main(String[] args){
	  //��ArrayDeque��ʵ��ջ��ArrayDeque��Deque�ӿڵ�ʵ���࣬��һ�����������˫�˶���
	  ArrayDeque<String> stack = new ArrayDeque<>();
	  stack.push("Monday");
	  stack.push("Tuseday");
	  stack.push("Wednesday");
	  stack.push("Thursday");
	  stack.push("Friday");
	  System.out.println(stack);
	  stack.pop();
	  System.out.println(stack);
	  
	  //��ArrayDeque��ʵ�ֶ���
	  ArrayDeque<String> queue = new ArrayDeque<>();
	  queue.offer("Saturday");
	  queue.offer("Sunday");
	  System.out.println(queue);
	  queue.peek();
	  System.out.println("queue peek method" + queue);
	  queue.poll();
	  System.out.println(queue);
  }
}

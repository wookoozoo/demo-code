package com.wookoozoo.other;

public class Test {

    private static final int PAGE_SIZE = 20;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int offset = 40;
        int pageNum1 = (offset / PAGE_SIZE) + ((offset % PAGE_SIZE == 0) ? 0 : 1);
        int leftSkip = (offset % PAGE_SIZE) -1 + ((offset % PAGE_SIZE) == 0 ? 20 : 0);

        System.out.println(pageNum1);
        System.out.println(leftSkip);
        
        
        System.out.println(Integer.MAX_VALUE);
    }

}

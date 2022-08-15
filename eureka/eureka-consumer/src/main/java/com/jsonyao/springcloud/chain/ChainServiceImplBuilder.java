package com.jsonyao.springcloud.chain;

/**
 * 责任链构造者
 */
public class ChainServiceImplBuilder {

    private AbstractChainServiceImpl head;
    private AbstractChainServiceImpl tail;

    public ChainServiceImplBuilder addChainService(AbstractChainServiceImpl next) {
        if(this.head == null) {
            this.head = this.tail = next;
        } else {
            this.tail.next(next);
            this.tail = next;
        }

        return this;
    }

    public ChainService build() {
        return this.head;
    }
}

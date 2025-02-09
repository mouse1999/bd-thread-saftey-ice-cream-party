package com.amazon.ata.threadsafety;

import com.amazon.ata.threadsafety.model.Flavor;

import java.util.LinkedList;
import java.util.Queue;

public class FlavorRequestQueue {
    private final Queue<Flavor> flavorQueue;

    public FlavorRequestQueue() {
        flavorQueue = new LinkedList<>();
    }

    public void needFlavor(Flavor flavor) {
        flavorQueue.add(flavor);
    }

    public Flavor nextNeededFlavor() {
        Flavor flavor = pollNeededFlavour();
        while (flavor == null) {
            try {
                Thread.sleep(10L);
                flavor = pollNeededFlavour();
            } catch (InterruptedException e) {
                System.out.println("!!!Interrupted waiting for flavor request!!!");
                e.printStackTrace();
                throw new RuntimeException("Interrupted waiting for flavor request!", e);
            }
        }
        return flavor;
    }
    private synchronized Flavor pollNeededFlavour() {
        return flavorQueue.poll();
    }

    public int requestCount() {
        return flavorQueue.size();
    }
}

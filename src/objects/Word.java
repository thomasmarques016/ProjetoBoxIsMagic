package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class Word extends GameObject implements Pushable{
    
    private String word;   // "box", "is", "win", "move" ou "stop"

    public Word(Point2D position, String word) {
        super(position);
        this.word = word;
    }
    
    public String getWord() {
        return word;
    }

    //VER MELHOR
    /** Todas as palavras tem imagem "word-<texto>" e a mesma camada. */
    @Override
    public String getName() {
        return "word-" + word;
    }


    @Override
    public int getLayer() {
        return 3;
    }
}

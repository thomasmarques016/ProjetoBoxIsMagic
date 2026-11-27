package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class Word extends GameObject{
    
    private String word;   // "box", "is", "win", "move" ou "stop"
    private WordType tipo;

    public Word(Point2D position, String word, WordType tipo) {
        super(position);
        this.word = word;
        this.tipo = tipo;
    }
    
    public String getWord() {
        return word;
    }

    /** Todas as palavras tem imagem "word-<texto>" e a mesma camada. */
    @Override
    public String getName() {
        return "word-" + word;
    }


    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public boolean isPushable(){
        return true;
    }

    public WordType tipo(){
        return this.tipo;
    } 

    @Override 
    public String getNameWithoutTraco(){
        return getWord();
    }
}

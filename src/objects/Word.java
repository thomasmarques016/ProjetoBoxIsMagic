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

    @Override
    public boolean isPushable(){
        return true;
    }

    @Override 
    public String getNameWithoutTraco(){
        return "word";
    }
}

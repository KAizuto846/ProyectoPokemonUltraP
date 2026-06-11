package UltraP;

import java.util.ArrayList;

public class Pokemon {

    private String nombre;
    private TipoPokemon tipo;
    private int nivel;
    private int hp;
    private int hpMax;
    private int ataque;
    private int defensa;
    private int velocidad;
    private ArrayList<Habilidad> habilidades; 
    
    public Pokemon(String nombre, TipoPokemon tipo, int nivel) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.hpMax = tipo.getHpBase() + (nivel * 5);
        this.hp = this.hpMax;
        this.ataque = tipo.getAtaqueBase() + (nivel * 3);
        this.defensa = 30 + (nivel * 2);
        this.habilidades = new ArrayList<>(); 
       }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPokemon getTipo() {
        return tipo;
    }

    public void setTipo(TipoPokemon tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

   




    public void agregarHabilidad(Habilidad h) {
        this.habilidades.add(h);
    }

    public ArrayList<Habilidad> getHabilidades() {
        return habilidades;
    }

    public boolean estaVivo() {
        return hp > 0;
    }

    @Override
    public String toString() {
        return nombre + " [" + tipo.getNombre() + "] LV." + nivel
             + " HP: " + hp + "/" + hpMax;
    }
}

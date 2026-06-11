package UltraP;

public enum TipoPokemon {

    FUEGO("Fuego","Charizard", "fuego.png", 120,50,65),
    AGUA("Agua","Piplup", "agua.png", 110, 45,45),
    PLANTA("Planta","Cacturne","planta.png", 130, 70,55),
    ELECTRICO("Eléctrico","Raikou", "electrico.png", 100, 55,90),
    SINIESTRO("Siniestro","Umbreon", "siniestro.png", 120,87,67 ),
    TLUCHA("Lucha","Blaziken", "siniestro.png", 140, 35,78),
    VOLADOR("Volador","Skarmory", "siniestro.png", 120, 55,68),
    PSIQUICO("Psiquico","Gardevoir", "siniestro.png", 150, 25,80),
    NORMAL("Normal","Snorlax", "siniestro.png", 100, 100,40);

    private final String tipo;
    private final String nombre;
    private final String iconoRuta;
    private final int hpBase;
    private final int ataqueBase;
    private final int velocidadBase;


    private TipoPokemon(String tipo, String nombre, String iconoRuta, int hpBase, int ataqueBase, int velocidadBase) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.iconoRuta = iconoRuta;
        this.hpBase = hpBase;
        this.ataqueBase = ataqueBase;
        this.velocidadBase = velocidadBase;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIconoRuta() {
        return iconoRuta;
    }

    public int getHpBase() {
        return hpBase;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getVelocidadBase() {
        return velocidadBase;
    }


   
    

    public static TipoPokemon aleatorio() {
        TipoPokemon[] tipos = values();
        return tipos[(int) (Math.random() * tipos.length)];
    }

   
    //buenas//  
    }


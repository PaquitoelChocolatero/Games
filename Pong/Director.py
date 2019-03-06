import pygame
from pygame.locals import * #Para facilitar capturas por teclado

WIDTH = 800
HEIGHT = 600

class Director:
    def __init__(self):
        self.escena = None
        self.quit_flag = False
        self.window = pygame.display.set_mode((WIDTH, HEIGHT)) #Creamos una ventana, ver docu de pygame para ver set_mode
        self.fps=pygame.time.Clock()#Frame Rate
        self.WIDTH = WIDTH
        self.HEIGHT=HEIGHT
        pygame.display.set_caption("Pong")

    def quit(self):#Metodo que corte el juego
        self.quit_flag=True

    def loop(self):

        while not self.quit_flag:
            time=self.fps.tick(40)
            for evento in pygame.event.get():
                if evento.type == pygame.KEYDOWN:#Comprobamos que se este presionando una tecla
                    teclas = pygame.key.get_pressed()#Crea un mapa de teclas
                    if teclas[K_ESCAPE]:#Si presionamos esc salimos
                        self.quit()
                if evento.type == pygame.QUIT:
                    self.quit()

                self.escena.eventos(evento)

            self.escena.actualizar()

            self.escena.pintar(self.window)

    def cambiar_escena(self, escena):
        self.escena = escena

import pygame

from Director import Director
from Escena import Menu

pygame.font.init()
pygame.mixer.pre_init(44100, -16, 2, 2048)#El pre_init se usa para que las cosas suenen al momento y sin retraso
pygame.mixer.init()                      #A más frecuencia y a más baja más grave

pygame.init()

game = Director()
menu = Menu(game)

game.cambiar_escena(menu)
game.loop()

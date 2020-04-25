import pygame
from pygame.locals import *

from Classes import *
director = Director()


pygame.font.init()
pygame.mixer.pre_init(44100, -16, 2, 2048)
pygame.mixer.init()


pygame.init()

#game = Game(director)
menu = Menu(director)
#victory = Failure(director)

director.change_scene(menu)
director.loop()

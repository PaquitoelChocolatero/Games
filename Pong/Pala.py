import pygame
from pygame.locals import *

class Pala(pygame.sprite.Sprite): #Heredamos de sprite porque pygame calcula solo las colisiones
                                  #Podemos usar sprite.Group para actualizar todo a la vez

    def __init__(self, x, y, is_player):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([20, 150])
        self.image.fill([255,255,255])
        self.rect=self.image.get_rect() #Para las colisiones
        self.rect.center = [x,y]
        self.is_player=is_player

    def mover(self, desplazamiento, height):
        if self.rect.centery+desplazamiento >= 65 and self.rect.centery+desplazamiento <= height-65: #70 es el margen para que no quede feucho
            self.rect.centery+=desplazamiento

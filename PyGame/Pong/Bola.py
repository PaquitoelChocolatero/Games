import pygame
import random

class Bola(pygame.sprite.Sprite):

    def __init__(self, x, y):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load("bola.png").convert_alpha() #.convert_alpha() hace que un png sea transparente
        self.image = pygame.transform.scale(self.image, (50, 50))
        self.vel = [10, 10] #Velocidad en x e y
        self.rect=self.image.get_rect() #Para las colisiones
        self.rect.center = [x,y]

    def update(self):
        self.rect.centerx+=self.vel[0]
        self.rect.centery+=self.vel[1]

    def reset(self, x, y):
        self.rect.center = [x,y]
        self.vel = [random.choice([-1, 1])*10, random.choice([-1, 1])*10]#Para que no vaya siempre en la misma dirección

    #No hace falta un metodo draw porque Group.draw lo hace solo

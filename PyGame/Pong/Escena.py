import pygame
from pygame.locals import *

class Escena:
    def __init__(self, Pong):
        self.Pong = Pong

    def eventos(self, evento):
        raise NotImplemented("Método eventos no implementado")

    def actualizar(self):
        raise NotImplemented("Método actualizar no implementado")

    def pintar(self, window):
        pygame.display.flip()

from Director import Director

difficulty=0

winner = -1

class Menu(Escena):
    def __init__(self, director):
        self.director = director

        self.background = pygame.image.load("fondo_menu.jpg")
        self.background = pygame.transform.scale(self.background, (self.director.WIDTH, self.director.HEIGHT))

        self.font = pygame.font.SysFont("Comic Sans", 80, True)
        self.title = self.font.render("PONG", True, (0, 0, 0))
        self.font2 = pygame.font.SysFont("Comic Sans", 30, True)
        self.message = self.font2.render("select a difficulty", True, (0, 0, 0))

        self.font3 = pygame.font.SysFont("Comic Sans", 50, True)
        self.easy = self.font3.render("EASY", True, (154,205,50))
        self.medium = self.font3.render("MEDIUM", True, (154,205,50))
        self.hard = self.font3.render("HARD", True, (154,205,50))

        self.sonido_menu = pygame.mixer.Sound("menu.wav")
        self.sonido_menu.play(-1)
        self.sonido_click = pygame.mixer.Sound("click.wav")

        self.arrow = pygame.image.load("arrow.png").convert_alpha()
        self.arrow = pygame.transform.scale(self.arrow, (50, 50))
        self.arrow_x=[self.director.WIDTH/3+30, self.director.WIDTH/3+5, self.director.WIDTH/3+25]
        self.arrow_y=[self.director.HEIGHT/3+63, self.director.HEIGHT/3+123, self.director.HEIGHT/3+183]
        self.arrow_pos = 0

    def eventos(self, evento):
        juego = Juego(self.director)

        if evento.type == pygame.KEYDOWN:
            teclas = pygame.key.get_pressed()
            if (teclas[K_s] or teclas[K_DOWN]) and self.arrow_pos<=1:
                self.sonido_click.play()
                self.arrow_pos+=1
            elif (teclas[K_s] or teclas[K_DOWN]) and self.arrow_pos==2:
                self.sonido_click.play()
                self.arrow_pos = 0
            elif (teclas[K_w] or teclas[K_UP]) and self.arrow_pos>=1:
                self.sonido_click.play()
                self.arrow_pos-=1
            elif (teclas[K_w] or teclas[K_UP]) and self.arrow_pos==0:
                self.sonido_click.play()
                self.arrow_pos = 2
            elif teclas[K_RETURN]:
                global difficulty
                if self.arrow_pos==0:
                    difficulty=4
                    self.sonido_menu.stop()
                    self.sonido_click.play()
                    self.director.cambiar_escena(juego)
                elif self.arrow_pos==1:
                    difficulty=6
                    self.sonido_menu.stop()
                    self.sonido_click.play()
                    self.director.cambiar_escena(juego)
                elif self.arrow_pos==2:
                    difficulty=7.5
                    self.sonido_menu.stop()
                    self.sonido_click.play()
                    self.director.cambiar_escena(juego)

    def actualizar(self):
        pass

    def pintar(self, window):
        window.blit(self.background, (0, 0))
        window.blit(self.title, (self.director.WIDTH/3+50, self.director.HEIGHT/3-50))
        window.blit(self.message, (self.director.WIDTH/3+40, self.director.HEIGHT*2/3-200))
        window.blit(self.easy, (self.director.WIDTH/3+85, self.director.HEIGHT/3+70))
        window.blit(self.medium, (self.director.WIDTH/3+60, self.director.HEIGHT/3+130))
        window.blit(self.hard, (self.director.WIDTH/3+80, self.director.HEIGHT/3+190))
        window.blit(self.arrow, (self.arrow_x[self.arrow_pos], self.arrow_y[self.arrow_pos]))
        pygame.display.flip()

from Pala import Pala
from Bola import Bola

class Juego(Escena):
    def __init__(self, director):
        self.director = director
        self.bola = Bola(self.director.WIDTH/2, self.director.HEIGHT/2)
        self.jugador = Pala(20, self.director.HEIGHT/2, True)
        self.cpu = Pala(self.director.WIDTH-20, self.director.HEIGHT/2, False)
        self.bola_group = pygame.sprite.GroupSingle(self.bola)
        self.palas = pygame.sprite.Group(self.jugador, self.cpu)
        self.desplazamiento=0

        self.background = pygame.image.load("fondo.jpg")
        self.background = pygame.transform.scale(self.background, (self.director.WIDTH, self.director.HEIGHT))

        self.p_jug = 0
        self.p_cpu = 0
        self.points = pygame.font.SysFont("Comic Sans", 50, True)
        self.p_jug_display = self.points.render(str(self.p_jug), True, (255, 255, 255))
        self.p_cpu_display = self.points.render(str(self.p_cpu), True, (255, 255, 255))

        pygame.mouse.set_visible(0)

        self.sonido_rebote = pygame.mixer.Sound("sound.wav")#Añadir archivo de audio

        self.sonido_punto = pygame.mixer.Sound("point.wav")

        self.music = pygame.mixer.Sound("winner.wav")

    def eventos(self, evento):
        self.desplazamiento=0
        if evento.type == pygame.KEYDOWN:
            teclas = pygame.key.get_pressed()
            if teclas[K_w] or teclas[K_UP]:
                self.desplazamiento=-10
            if teclas[K_s] or teclas[K_DOWN]:
                self.desplazamiento=10

    def actualizar(self):
        self.check()
        self.bola_group.update()
        self.jugador.mover(self.desplazamiento, self.director.HEIGHT)
        self.actualizar_cpu()
        self.end_game()

    def end_game(self):
        victoria = Victoria(self.director)
        global winner
        if self.p_jug >= 10:
            winner = 0
            self.director.cambiar_escena(victoria)
            self.music.play(-1)
        elif self.p_cpu >= 10:
            winner = 1
            self.director.cambiar_escena(victoria)
            self.music.play(-1)


    def check(self):
        if self.bola.rect.centery>=self.director.HEIGHT or self.bola.rect.centery<=0:#Si llega a los limites en y se invierte
            self.bola.vel[1]=-self.bola.vel[1]

        elif pygame.sprite.collide_rect(self.jugador, self.bola) or pygame.sprite.collide_rect(self.bola, self.cpu):#Si colisiona con una pala se invierte la x
            self.bola.vel[0]=-self.bola.vel[0]
            self.sonido_rebote.play()

        elif self.bola.rect.centerx>=self.cpu.rect.centerx+10:#Si alguien pierde reseteamos la bola al medio
            self.bola.reset(self.director.WIDTH/2, self.director.HEIGHT/2)
            self.sonido_punto.play()
            self.p_jug+=1
            self.p_jug_display = self.points.render(str(self.p_jug), True, (255, 255, 255))

        elif self.bola.rect.centerx<=self.jugador.rect.centerx-10:#Si alguien pierde reseteamos la bola al medio
            self.bola.reset(self.director.WIDTH/2, self.director.HEIGHT/2)
            self.sonido_punto.play()
            self.p_cpu+=1
            self.p_cpu_display = self.points.render(str(self.p_cpu), True, (255, 255, 255))

    def actualizar_cpu(self):#Seguimos la bola
        if self.bola.rect.centery<self.cpu.rect.centery and self.cpu.rect.centery >= 70 :
            self.cpu.rect.centery-=difficulty
        elif self.bola.rect.centery>self.cpu.rect.centery and self.cpu.rect.centery <= self.director.HEIGHT-70:
            self.cpu.rect.centery+=difficulty

    def pintar(self, window):
        window.blit(self.background, (0, 0))
        window.blit(self.p_jug_display, (50, 50))
        window.blit(self.p_cpu_display, (self.director.WIDTH-50, 50))
        self.bola_group.draw(window)
        self.palas.draw(window)
        pygame.display.flip()#Actualizamos la ventana

class Victoria(Escena):
    def __init__(self, director):
        self. director = director

        self.background = pygame.image.load("victoria.jpg")
        self.background = pygame.transform.scale(self.background, (self.director.WIDTH, self.director.HEIGHT))

        self.font = pygame.font.SysFont("Comic Sans", 100, True)
        self.font2 = pygame.font.SysFont("Comic Sans", 30)

        self.jug = self.font.render("YOU WIN!!", True, (255, 255, 255))
        self.cpu = self.font.render("THE CPU WINS!!", True, (255, 255, 255))

        self.text = self.font2.render("press esc to exit", True, (255, 255, 255))

    def eventos(self, escena):
        pass

    def actualizar(self):
        pass

    def pintar(self, window):
        window.blit(self.background, (0,0))
        if winner == 0:
            window.blit(self.jug, (self.director.WIDTH/4, self.director.HEIGHT/3+100))
        elif winner == 1:
            window.blit(self.cpu, (90, self.director.HEIGHT/3+100))
        window.blit(self.text, (self.director.WIDTH/4+115, self.director.HEIGHT-50))
        pygame.display.flip()#Actualizamos la ventana

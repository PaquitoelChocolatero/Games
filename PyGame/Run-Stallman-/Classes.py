import pygame
from pygame.locals import *
import random

#Constantes
WIDTH = 1000
HEIGHT = 600
ACCELERATION = 0.5
WIDTH_PERSONAJE = 80
HEIGHT_PERSONAJE = 110
#Cambiar a 10000
TOTAL_LEVEL_WIDTH = 2030
TOTAL_LEVEL_HEIGHT = 800
VEL_MASA = 6 #6
INIT_MASA = -600

VEL_INIT=-15
MAX_VEL = 10
MIN_VEL = -10

difficulty=-1

#Clase Director. Maneja las escenas y lleva el loop de juego
class Director:
    #Constructor
    def __init__(self):
        self.escena = None
        self.quit_flag = False
        self.window = pygame.display.set_mode((WIDTH, HEIGHT)) #Creamos una ventana, ver docu de pygame para ver set_mode
        self.fps=pygame.time.Clock() #Frame Rate
        pygame.display.set_caption("\t\t\t\t\t\t\t\t\t\t\t Run, Stallman, RUN!!") #Titulo de la ventana
        pygame.mouse.set_visible(0) #Ocultamos el raton

    def quit(self):#Metodo que corte el juego
        self.quit_flag=True

    #Loop de juego
    def loop(self):
        while not self.quit_flag:
            time=self.fps.tick(40) #El juego corre a 40 FPS
            for event in pygame.event.get():
                if event.type == pygame.KEYDOWN:#Comprobamos que se este presionando una tecla
                    keys = pygame.key.get_pressed()#Crea un mapa de teclas
                    if keys[K_ESCAPE]:#Si presionamos esc salimos
                        self.quit()
                if event.type == pygame.QUIT: #Si se cierra la ventana, salimos
                    self.quit()
                self.scene.events(event) #Pasamos los eventos a la escena
            self.scene.update() #Actualizamos la escena
            self.scene.draw(self.window) #Dibujamos la escena

    #Método para cambiar la escena ejecutandose
    def change_scene(self, scene):
        self.scene = scene

#Interfaz de escena
class Scene:
    def __init__(self):
        raise NotImplemented("Method init hasn't been implemented")

    def events(self, event):
        raise NotImplemented("Method events hasn't been implemented")

    def update(self):
        raise NotImplemented("Method update hasn't been implemented")

    def draw(self, window):
        pygame.display.flip()

#Escena de menú principal
class Menu(Scene):
    def __init__(self, director):
        self.director = director
        #background
        self.background = pygame.image.load("menu.jpg")
        self.background = pygame.transform.scale(self.background, (WIDTH, HEIGHT))
        #Titulo del juego
        self.font = pygame.font.SysFont("Comic Sans", 80, True)
        self.title = self.font.render("Run, Stallman, RUN!!", True, (255, 255, 255))
        self.font2 = pygame.font.SysFont("Comic Sans", 30, True)
        self.message = self.font2.render("select a difficulty", True, (255, 255, 255))
        #Opciones de dificultad
        self.font3 = pygame.font.SysFont("Comic Sans", 50, True)
        self.easy = self.font3.render("EASY", True, (255, 255, 255))
        self.hard = self.font3.render("HARD", True, (255, 255, 255))
        #Sonidos de menú
        self.sonido_menu = pygame.mixer.Sound("menu.wav")
        self.sonido_menu.play(-1)
        self.sonido_click = pygame.mixer.Sound("click.wav")
        #Flecha para seleccionar
        self.arrow = pygame.image.load("arrow.png").convert_alpha()
        self.arrow = pygame.transform.scale(self.arrow, (50, 50))
        self.arrow_x=[200, 200]
        self.arrow_y=[HEIGHT/3+63, HEIGHT/3+123]
        self.arrow_pos = 0

    #Maneja los eventos de la escena
    def events(self, evento):
        if evento.type == pygame.KEYDOWN:
            #Obtenemos las teclas pulsadas
            teclas = pygame.key.get_pressed()
            #Baja la opcion si no está abajo del todo
            if (teclas[K_s] or teclas[K_DOWN]) and self.arrow_pos<1:
                self.sonido_click.play()
                self.arrow_pos+=1
            #La opcion seleccionada es la primera al bajar estando al final de la lista
            elif (teclas[K_s] or teclas[K_DOWN]) and self.arrow_pos==1:
                self.sonido_click.play()
                self.arrow_pos = 0
            #Sube la opcion si no está arriba del todo
            elif (teclas[K_w] or teclas[K_UP]) and self.arrow_pos>0:
                self.sonido_click.play()
                self.arrow_pos-=1
            #La opcion seleccionada es la ultima al subir estando al principio de la lista
            elif (teclas[K_w] or teclas[K_UP]) and self.arrow_pos==0:
                self.sonido_click.play()
                self.arrow_pos = 1
            #Cambiamos de escena con la opcion de dificultad introducida
            elif teclas[K_RETURN]:
                global difficulty
                if self.arrow_pos==0:
                    #Easy
                    difficulty=0
                elif self.arrow_pos==1:
                    #Hard
                    difficulty=1
                #Detenemos la musica y cambiamos la escena
                self.sonido_menu.stop()
                self.sonido_click.play()
                self.director.change_scene(Game(self.director))

    #La escena de menu se actualiza en los eventos de teclas solamente
    def update(self):
        pass

    #Dibujamos la escena
    def draw(self, window):
        window.blit(self.background, (0, 0))
        window.blit(self.title, (25, HEIGHT/3-50))
        window.blit(self.message, (215, HEIGHT*2/3-175))
        window.blit(self.easy, (255, HEIGHT/3+70))
        window.blit(self.hard, (255, HEIGHT/3+130))
        window.blit(self.arrow, (self.arrow_x[self.arrow_pos], self.arrow_y[self.arrow_pos]))
        pygame.display.flip()

#Escena juego. Dibuja el nivel dependiendo de la dificultad y controla el movimiento del personaje, la masa y de la camara
class Game(Scene):
    #Constructor
    def __init__(self, director):
        self.director=director

        self.background = pygame.image.load("background.jpg")
        self.background = pygame.transform.scale(self.background, (WIDTH, HEIGHT))

        self.blocks = pygame.sprite.Group()
        self.pos0=35
        #Grupo con todos los sprites que la camara dibuja
        self.entities = pygame.sprite.Group()
        self.pos1=35
        self.pos2=35
        #Personaje principal
        self.stallman = Stallman(50, TOTAL_LEVEL_HEIGHT-125, self.director)
        self.player_group = pygame.sprite.GroupSingle(self.stallman)
        #Sonido de fondo
        self.sonido_juego = pygame.mixer.Sound("music.wav")
        self.sonido_juego.play(-1)

        global TOTAL_LEVEL_WIDTH
        #Dificultad facil
        if difficulty==0:
            #Nivel prediseñado
            self.list0=(5, 6, 12, 13, 20, 21, 22, 28, 29)
            self.list1=(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 20, 21, 25, 26, 27, 28, 29)
            self.list2=(4, 5, 6, 7, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
            #Dibuja el nivel
            for i in range(1,30):
                if not (i in self.list1):
                    self.blocks.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-221))
                    self.entities.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-221))

                if not (i in self.list0):
                    self.blocks.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-35))
                    self.entities.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-35))

                if not (i in self.list2):
                    self.blocks.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-407))
                    self.entities.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-407))
                self.pos0+=70

            #Añadimos a mano las paredes de la fila 0
            self.blocks.add(Block(35*21, TOTAL_LEVEL_HEIGHT-105))
            self.entities.add(Block(35*21, TOTAL_LEVEL_HEIGHT-105))
            self.blocks.add(Block(35*21, TOTAL_LEVEL_HEIGHT-175))
            self.entities.add(Block(35*21, TOTAL_LEVEL_HEIGHT-175))

            self.blocks.add(Block(35*27, TOTAL_LEVEL_HEIGHT-105))
            self.entities.add(Block(35*27, TOTAL_LEVEL_HEIGHT-105))
            self.blocks.add(Block(35*27, TOTAL_LEVEL_HEIGHT-175))
            self.entities.add(Block(35*27, TOTAL_LEVEL_HEIGHT-175))

            self.blocks.add(Block(35*37, TOTAL_LEVEL_HEIGHT-105))
            self.entities.add(Block(35*37, TOTAL_LEVEL_HEIGHT-105))
            self.blocks.add(Block(35*37, TOTAL_LEVEL_HEIGHT-175))
            self.entities.add(Block(35*37, TOTAL_LEVEL_HEIGHT-175))

            #Añadimos a mano las paredes de la fila 1
            self.blocks.add(Block(35*47, TOTAL_LEVEL_HEIGHT-291))
            self.entities.add(Block(35*47, TOTAL_LEVEL_HEIGHT-291))
            self.blocks.add(Block(35*47, TOTAL_LEVEL_HEIGHT-361))
            self.entities.add(Block(35*47, TOTAL_LEVEL_HEIGHT-361))
        #Dificultad dificil
        if difficulty==1:
            #Mapa de nivel más grande
            TOTAL_LEVEL_WIDTH=10000
            self.blocks.add(Block(self.pos0,TOTAL_LEVEL_HEIGHT-35))
            self.entities.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-35))
            #El nivel se genera aleatoriamente
            for i in range(1,700):
                self.pos1+=70
                self.pos0+=70
                self.pos2+=70
                if random.choice([0,1])==1:
                    self.blocks.add(Block(self.pos1, TOTAL_LEVEL_HEIGHT-221))
                    self.entities.add(Block(self.pos1, TOTAL_LEVEL_HEIGHT-221))

                if random.choice([0,1])==1:
                    self.blocks.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-35))
                    self.entities.add(Block(self.pos0, TOTAL_LEVEL_HEIGHT-35))

                if random.choice([0,1])==1:
                    self.blocks.add(Block(self.pos2, TOTAL_LEVEL_HEIGHT-407))
                    self.entities.add(Block(self.pos2, TOTAL_LEVEL_HEIGHT-407))
                else:
                    self.pos1+=40
                    self.pos0+=40
                    self.pos2+=40

        #Camara
        self.camera = Camera(TOTAL_LEVEL_WIDTH, TOTAL_LEVEL_HEIGHT)

        self.entities.add(self.stallman)
        #Masa de enemigos
        self.masa = Masa()
        self.entities.add(self.masa)
        #Final del mapa
        self.end = End()
        self.entities.add(self.end)
    #Eventos de la escena
    def events(self, event):
        keys = pygame.key.get_pressed()
        #Actualizacion de aceleraciones segun la tecla pulsada
        if keys[K_d] or keys[K_RIGHT]:
            self.stallman.acc[0] = ACCELERATION
        elif keys[K_a] or keys[K_LEFT]:
            self.stallman.acc[0] = -ACCELERATION
        #Si no hay teclas pulsadas deceleramos
        else:
            if self.stallman.vel[0] > 0:
                self.stallman.acc[0] = -ACCELERATION
            elif self.stallman.vel[0] < 0:
                self.stallman.acc[0] = ACCELERATION
            else:
                self.stallman.acc[0] = 0
        #Metodo de salto
        if (keys[K_w] or keys[K_SPACE] or keys[K_UP]):
            self.stallman.jump(self.blocks)
    #Actualiza la escena
    def update(self):
        #Actualiza la camara segun la posicion del personaje
        self.camera.update(self.stallman)
        #Actualiza el personaje
        self.player_group.update(self.blocks, self.masa.rect.right, self.sonido_juego)
        #Actualiza la masa
        self.masa.update()

    #Dibuja la escena usando la camara
    def draw(self, window):
        #Background
        window.blit(self.background, (0,0))
        #Elementos de la escena
        for e in self.entities:
            window.blit(e.image, self.camera.apply(e))
        #Masa
        window.blit(self.masa.image, self.camera.apply(self.masa))
        #Meta
        window.blit(self.end.image, self.camera.apply(self.end))
        pygame.display.flip()

#Escena de victoria, devuelve al menu principal
class Victory(Scene):
    def __init__(self, director):
        self. director = director
        #Background
        self.background = pygame.image.load("victory.jpg")
        self.background = pygame.transform.scale(self.background, (WIDTH, HEIGHT))
        #Mensajes informativos
        self.font = pygame.font.SysFont("Comic Sans", 100, True)
        self.font2 = pygame.font.SysFont("Comic Sans", 30)

        self.jug = self.font.render("YOU WIN!!", True, (218, 165, 32))

        self.text_esc = self.font2.render("press esc to exit", True, (218, 165, 32))
        self.text_enter = self.font2.render("press enter to restart", True, (218, 165, 32))
        #Sonido de la escena
        self.sonido_victoria = pygame.mixer.Sound("win.wav")
        self.sonido_victoria.play()
    #CAptura los eventos de la escena
    def events(self, escena):
        teclas = pygame.key.get_pressed()
        #Devuelve al menu principal al pulsar enter
        if teclas[K_RETURN]:
            self.director.change_scene(Menu(self.director))

    #No hay actualizacion de escena
    def update(self):
        pass

    #Dibuja la escena
    def draw(self, window):
        window.blit(self.background, (0,0))
        window.blit(self.jug, (WIDTH/4+50, HEIGHT/3+50))
        window.blit(self.text_esc, (WIDTH/4+160, HEIGHT-100))
        window.blit(self.text_enter, (WIDTH/4+140, HEIGHT-50))
        pygame.display.flip()

#Escena de derrota. Devuelve al menú principal
class Failure(Scene):
    def __init__(self, director):
        self. director = director
        #Background
        self.background = pygame.image.load("eres_un_loser.jpg")
        self.background = pygame.transform.scale(self.background, (WIDTH, HEIGHT))
        #Mensajes informativos
        self.font = pygame.font.SysFont("Comic Sans", 100, True)
        self.font2 = pygame.font.SysFont("Comic Sans", 30)

        self.jug = self.font.render("YOU LOSE :'(", True, (155, 179, 219))

        self.text_esc = self.font2.render("press esc to accept defeat", True, (155, 179, 219))
        self.text_enter = self.font2.render("press enter to try again", True, (155, 179, 219))
        #Música de la escena
        self.sonido_derrota = pygame.mixer.Sound("lose.wav")
        self.sonido_derrota.play()

    #Captura los eventos de la escena
    def events(self, escena):
        teclas = pygame.key.get_pressed()
        #Devuelve al menú principal
        if teclas[K_RETURN]:
            self.director.change_scene(Menu(self.director))

    #Esta escena no se actualiza
    def update(self):
        pass

    #Dibuja la escena
    def draw(self, window):
        window.blit(self.background, (0,0))
        window.blit(self.jug, (WIDTH/4, HEIGHT/4-50))
        window.blit(self.text_esc, (WIDTH/4+118, HEIGHT-100))
        window.blit(self.text_enter, (WIDTH/4+129, HEIGHT-50))
        pygame.display.flip()

#Clase para los bloques, de tipo Sprite
class Block(pygame.sprite.Sprite):
    #Constructor
    def __init__(self, x, y):
        pygame.sprite.Sprite.__init__(self)
        #Imagen
        self.image = pygame.image.load("platformIndustrial_043.png").convert_alpha()
        #Hitbox
        self.rect = self.image.get_rect()
        self.rect.center = [x, y]

#Clase para la masa enfurecida, de tipo Sprite
class Masa(pygame.sprite.Sprite):
    #Constructor
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image =  pygame.image.load("masa_enfurecida.png").convert_alpha()
        self.rect = self.image.get_rect()
        self.rect.right = INIT_MASA

    #Update para desplazar la masa
    def update(self):
        self.rect.right += VEL_MASA

#Clase para la meta, de tipo sprite
class End(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image =  pygame.image.load("meta2.png").convert_alpha()
        self.rect = self.image.get_rect()
        self.rect.right = TOTAL_LEVEL_WIDTH

#Clase para el personaje, de tipo sprite
class Stallman(pygame.sprite.Sprite):
    def __init__(self, x, y, director):
        self.director=director

        pygame.sprite.Sprite.__init__(self)
        self.spriteSheet = pygame.image.load("player_tilesheet2.png").convert_alpha()
        self.image = self.spriteSheet.subsurface((0,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE))
        self.rect = self.image.get_rect()
        self.rect.center = [x, y]
        self.vel=[0, 0]
        self.acc=[0, 0]
        self.able_to_jump=False
        self.Frame = 0

        self.sonido_saltar = pygame.mixer.Sound("jump.wav")
    #Metodo para saltar si se puede
    def jump(self, blocks):
        self.rect.y += 2
        list_collides = pygame.sprite.spritecollide(self, blocks, False)
        self.rect.y -=2

        if len(list_collides)>0:
            self.vel[1]=VEL_INIT
            self.sonido_saltar.play()

    def update(self, blocks, posMasa, sonido_juego):
        keys = pygame.key.get_pressed()
        if not (keys[K_d] or keys[K_RIGHT] or keys[K_a] or keys[K_LEFT]) and self.vel[0]==0:
            self.acc[0]=0
        if (self.vel[0]+self.acc[0] <= MAX_VEL and self.vel[0]+self.acc[0] >= MIN_VEL):
            self.vel[0] += self.acc[0]
        if not self.able_to_jump:
            self.vel[1] += ACCELERATION

        if self.rect.left + self.vel[0] > 0:
            self.rect.left += self.vel[0]


        if self.vel[1] < 0:
            if self.vel[0] < 0:
                self.image = pygame.transform.flip(self.spriteSheet.subsurface((WIDTH_PERSONAJE,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE)), True, False)
            else:
                self.image = self.spriteSheet.subsurface((WIDTH_PERSONAJE,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE))
        elif self.vel[1] > 1:
            if self.vel[0] < 0:
                self.image = pygame.transform.flip(self.spriteSheet.subsurface((2*WIDTH_PERSONAJE,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE)), True, False)
            else:
                self.image = self.spriteSheet.subsurface((2*WIDTH_PERSONAJE,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE))
        else:
            self.image = self.spriteSheet.subsurface((0,0,WIDTH_PERSONAJE,HEIGHT_PERSONAJE))
            index = 0
            if self.Frame > 19:
                index = 1
            if self.vel[0] < 0:
                self.image = pygame.transform.flip(self.spriteSheet.subsurface((index*WIDTH_PERSONAJE,HEIGHT_PERSONAJE,WIDTH_PERSONAJE,HEIGHT_PERSONAJE)), True, False)
            elif self.vel[0] > 0:
                self.image = self.spriteSheet.subsurface((index*WIDTH_PERSONAJE,HEIGHT_PERSONAJE,WIDTH_PERSONAJE,HEIGHT_PERSONAJE))


        # do x-axis collisions
        self.collide(self.vel[0], 0, blocks)

        self.rect.top += self.vel[1]
        self.able_to_jump=False
        # do y-axis collisions
        self.collide(0, self.vel[1], blocks)
        self.Frame += 1
        if(self.Frame > 40):
            self.Frame = 0
        self.check(posMasa, sonido_juego)
    #Método para detectar colisiones y actualizar las velocidades en consecuencia
    def collide(self, velx, vely, platforms):
        for p in platforms:
            if pygame.sprite.collide_rect(self, p):
                if velx > 0:
                    self.rect.right = p.rect.left
                    self.vel[0] = 0
                if velx < 0:
                    self.rect.left = p.rect.right
                    self.vel[0] = 0
                if vely > 0:
                    self.rect.bottom = p.rect.top
                    self.able_to_jump = True
                    self.vel[1] = 0
                if vely < 0:
                    self.rect.top = p.rect.bottom
                    self.vel[1] = 0
    #Metodo para detectar la muerte o la victoria del personaje
    def check(self, posMasa, sonido_juego):
        if self.rect.top >= TOTAL_LEVEL_HEIGHT or self.rect.right <= posMasa:
            sonido_juego.stop()
            self.director.change_scene(Failure(self.director))
        if self.rect.left >= TOTAL_LEVEL_WIDTH:
            sonido_juego.stop()
            self.director.change_scene(Victory(self.director))
#Clase camara, que maneja la camara del juego, y desplaza sprites
class Camera(object):
    def __init__(self, width, height):
        self.state = Rect(0, 0, width, height)

    def apply(self, target):
        return target.rect.move(self.state.topleft)

    def update(self, target):
        self.state = self.complex_camera(self.state, target.rect)

    def complex_camera(self, camera, target_rect):
        l, t, _, _ = target_rect
        _, _, w, h = camera
        l, t, _, _ = -l+int(WIDTH/2), -t+int(HEIGHT/2), w, h

        l = min(0, l)                           # stop scrolling at the left edge
        l = max(-(camera.width-WIDTH), l)   # stop scrolling at the right edge
        t = max(-(camera.height-HEIGHT), t) # stop scrolling at the bottom
        t = min(0, t)                           # stop scrolling at the top
        return Rect(l, t, w, h)

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{

    public float maxSpeed = 5f;
    public float speed = 2f;
    private Rigidbody2D rigidbody2D;
    private Animator animation;

    // Start is called before the first frame update
    void Start()
    {
        rigidbody2D = GetComponent<Rigidbody2D>();  //Motor físico
        animation = GetComponent<Animator>();   //Componente con las animaciones del objeto
    }

    // Update is called once per frame
    void Update()
    {
        animation.SetFloat("Speed", Mathf.Abs(rigidbody2D.velocity.x)); //Pasamos al animador la velocidad del jugador para calcular la animación
    }

    void FixedUpdate()
    {
        float h = Input.GetAxis("Horizontal"); //Cómo se mueve en horizontal?

        rigidbody2D.AddForce(Vector2.right * speed * h);    //Añadir fuerza horizontal al motor físico
        
        float limitVel = Mathf.Clamp(rigidbody2D.velocity.x, -maxSpeed, maxSpeed);  //Clamp mantieneel primer parámetro entre los dos siguientes
        rigidbody2D.velocity = new Vector2(limitVel, rigidbody2D.velocity.y);

        //Invertimos el sprite si está yendo a la izquierda
        if(h>0.1f) transform.localScale = new Vector3(1f, 1f, 1f);
        else if(h<-0.1f) transform.localScale = new Vector3(-1f, 1f, 1f);
        
    }
}

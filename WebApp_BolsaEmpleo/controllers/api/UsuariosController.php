<?php
/**
 * Created by PhpStorm.
 * User: Ezam
 * Date: 03/11/2017
 * Time: 04:51 PM
 */

namespace app\controllers\api;
use app\models\Usuario;
use Yii;
use yii\rest\ActiveController;
use app\models\api\Usuarios;

class UsuariosController extends ActiveController
{
    public $modelClass = 'app\models\api\Usuarios';

    public function actionSignup()
    {
        $request = Yii::$app->request;
        $response = Yii::$app->response;

        $usuario = new Usuarios();
        $usuario->load( $request->post());
        if( $usuario->validate() )
        {
            $response->statusCode = 200;
        } else {
            $response->statusCode = 400;
            $response->data = $usuario->getErrors();
        }

        $response->send();
    }

    public function actionLogin()
    {
        $request = Yii::$app->request;
        $response = Yii::$app->response;
        $usuario = $email = "";

        $response->format = \yii\web\Response::FORMAT_JSON;

        if ( ! $request->isPost)
        {
            $response->statusCode = 400;
        } else {
            $email = $request->post('email');
            $clave = $request->post('password');

            if( $email && $clave && $usuario = Usuarios::findUsername(trim($email) ) )
            {
                if( $usuario->validatePassword($clave) )
                {
                    $response->statusCode = 200;
                    $response->data = $usuario;

                } else {
                    $response->statusCode = 401;
                    $response->data = ['password'=>'Contraseña incorrecta'];
                }

            } else {
                $response->statusCode = 401;
                $response->data = ['email'=>'Correo no registrado'];
            }
        }
        $response->send();
    }
}
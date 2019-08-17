<?php
/**
 * Created by PhpStorm.
 * User: Ezam
 * Date: 26/10/2017
 * Time: 10:34 PM
 */

namespace app\models;
use Yii;
use yii\base\model;
use app\models\Usuario;
use yii\db\ActiveQuery;

class SignupForm extends \yii\db\ActiveRecord
{
    public $username;
    public $email;
    public $password;
    public $passwordConfirm;

    public static function tableName()
    {
        return 'usuarios';
    }

    public function attributeLabels()
    {
        return [
            'username' => 'Nombre de usuario',
            'email' => 'Correo',
            'password' => 'Contraseña',
            'passwordConfirm' => 'Confirmación'

        ];
    }

    public function fields()
    {
        return [
            'username', 'email'
        ];
    }

    public function rules()
    {
        return [
            [['username','email','password','passwordConfirm'], 'required', 'message' => 'campo requerido'],
            [['email','username'], 'unique', 'message' => 'El {attribute} {value} ya se encuentra registrado'],

            ['username', 'string', 'length' => [5,20], 'message' => 'longitud inadecuada. min 5, max 20' ],
            ['username', 'match', 'pattern' => '/^[0-9a-z._-]+$/i', 'message' => 'Solo se aceptan letras, números, puntos y guinoes'],

            ['email', 'email', 'message' => 'Ingresa un correo valido'],
            ['password', 'string', 'length' => [8,30], 'message' => 'longitud inadecuada. min 8, max 30'],
            ['passwordConfirm', 'compare', 'compareAttribute' => 'password', 'message' => 'las contraseñas no coinciden' ]
        ];

    }

    public function signup()
    {
        if($this->validate())
        {
            $usuario = new Usuario();
            $usuario->username = $this->username;
            $usuario->email = $this->email;
            $usuario->password = $this->password;

            if( $usuario->save() )
            {
                $auth = \Yii::$app->authManager;
                $authorRole = $auth->getRole('person');
                $auth->assign($authorRole, $usuario->getId() );
                return true;
            }

        }

        return false;
    }

}
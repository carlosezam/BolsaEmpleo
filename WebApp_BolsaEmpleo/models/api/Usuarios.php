<?php
/**
 * Created by PhpStorm.
 * User: Ezam
 * Date: 03/11/2017
 * Time: 05:15 PM
 */

namespace app\models\api;

use YII;
class Usuarios extends \yii\db\ActiveRecord
{


    public static function tableName()
    {
        return 'usuarios';
    }

    public function fields()
    {
        return [
            'id',
            'username',
            'email',
            'password',
            'auth_key' => 'authKey',
            'access_token' => 'accessToken',
            'active'
        ];
    }

    public function rules()
    {
        return [
            [['username','email','password'], 'required', 'message' => 'campo requerido'],
            [['email','username'], 'unique', 'message' => 'El {attribute} {value} ya se encuentra registrado'],

            ['username', 'string', 'length' => [5,20], 'message' => 'longitud inadecuada. min 5, max 20' ],
            ['username', 'match', 'pattern' => '/^[0-9a-z._-]+$/i', 'message' => 'Solo se aceptan letras, números, puntos y guinoes'],

            ['email', 'email', 'message' => 'Ingresa un correo valido'],
            ['password', 'string', 'length' => [8,30], 'message' => 'longitud inadecuada. min 8, max 30']
        ];

    }

    public function beforeSave($insert)
    {
        if(! parent::beforeSave($insert))
        {
            return false;
        }

        if( $this->isNewRecord )
        {
            $this->password = Yii::$app->getSecurity()->generatePasswordHash($this->password);
            $this->authKey = Yii::$app->getSecurity()->generateRandomString();
            $this->accessToken = Yii::$app->getSecurity()->generateRandomString();
        }

        return true;
    }

    public static function findUsername($data)
    {
        return static::find()
            ->where(['username'=>$data])
            ->orWhere(['email'=>$data])
            ->one();
    }

    public function validatePassword($password)
    {

        return \Yii::$app->getSecurity()->validatePassword($password, $this->password);
        //return $password === 'pass';
    }
}
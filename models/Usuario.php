<?php
namespace app\models;
use Yii;
use yii\db\ActiveRecord;
use yii\web\IdentityInterface;

class Usuario extends ActiveRecord implements IdentityInterface
{

    public static function tableName()
    {
        return 'usuarios';
    }

    /**
     * Finds an identity by the given ID.
     *
     * @param string|int $id the ID to be looked for
     * @return IdentityInterface|null the identity object that matches the given ID.
     */
    public static function findIdentity($id)
    {
        return static::findOne($id);
    }

    /**
     * Finds an identity by the given token.
     *
     * @param string $token the token to be looked for
     * @return IdentityInterface|null the identity object that matches the given token.
     */
    public static function findIdentityByAccessToken($token, $type = null)
    {
        return static::findOne(['access_token' => $token]);
    }

    public static function findByUsernameOrEmail($data)
    {
        return static::find()
                    ->where(['username'=>$data])
                    ->orWhere(['email'=>$data])
                    ->one();
    }
    /**
     * @return int|string current user ID
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return string current user auth key
     */
    public function getAuthKey()
    {
        return $this->authKey;
    }

    /**
     * @param string $authKey
     * @return bool if auth key is valid for current user
     */
    public function validateAuthKey($authKey)
    {
        return $this->getAuthKey() === $authKey;
    }


    public function validatePassword($password)
    {
        
        return Yii::$app->getSecurity()->validatePassword($password, $this->password);
        //return $password === 'pass';
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


}
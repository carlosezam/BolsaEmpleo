<?php

namespace app\models\api;

use Yii;

/**
 * This is the model class for table "lenguajes".
 *
 * @property integer $id
 * @property string $nombre
 * @property integer $habla
 * @property integer $lee
 * @property integer $escribe
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Lenguajes extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'lenguajes';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['nombre', 'habla', 'lee', 'escribe', 'id_usuario'], 'required', 'message' => 'Campo {attribute} requerido'],
            [['habla', 'lee', 'escribe', 'id_usuario'], 'integer'],
            [['nombre'], 'string', 'max' => 150],
            [['id_usuario'], 'exist', 'skipOnError' => true, 'targetClass' => Usuarios::className(), 'targetAttribute' => ['id_usuario' => 'id']],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nombre' => 'Nombre',
            'habla' => 'Habla',
            'lee' => 'Lee',
            'escribe' => 'Escribe',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public static function curriculumFields()
    {
        return [
            'nombre' => 'Lenguaje', 'habla' => 'Habla %', 'lee' => 'Lee %', 'escribe' => 'Escribe %'
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getIdUsuario()
    {
        return $this->hasOne(Usuarios::className(), ['id' => 'id_usuario']);
    }
}

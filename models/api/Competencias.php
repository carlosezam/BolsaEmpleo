<?php

namespace app\models\api;

use app\models\Usuario;
use Yii;

/**
 * This is the model class for table "competencias".
 *
 * @property integer $id
 * @property string $nombre
 * @property string $descripcion
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Competencias extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'competencias';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['nombre', 'descripcion', 'id_usuario'],
                'required', 'message' => 'Campo {attribute} requerido'],
            [['id_usuario'], 'integer'],
            [['nombre'],
                'string', 'message' => 'Ingresa un texto',
                'length' => [5,50],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 50 caracteres'
                ],
            [['descripcion'], 'string',
                'length' => [5,300],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 300 caracteres'
            ],
            [['id_usuario'], 'exist', 'skipOnError' => true, 'targetClass' => Usuario::className(), 'targetAttribute' => ['id_usuario' => 'id']],
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
            'descripcion' => 'Descripcion',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public static function curriculumFields()
    {
        return [
            'nombre' => 'Nombre', 'descripcion' => 'Descripción'
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

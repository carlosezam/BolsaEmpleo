<?php

namespace app\models\api;

use app\models\Usuario;
use Yii;

/**
 * This is the model class for table "escolares".
 *
 * @property integer $id
 * @property string $escuela
 * @property integer $no_nivel
 * @property string $documento
 * @property string $profesion
 * @property string $fecha_ini
 * @property string $fecha_fin
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Escolares extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'escolares';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['escuela', 'no_nivel', 'documento', 'fecha_ini', 'fecha_fin', 'id_usuario'],
                'required', 'message' => 'Campo {attribute} requerido'],
            [['no_nivel', 'id_usuario'], 'integer'],
            [['fecha_ini', 'fecha_fin'],
                'date', 'format' => 'yyyy-MM-dd','message' => 'Formato de fecha no valido'],
            [['escuela'], 'string', 'length' => [5,300],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 50 caracteres'],
            [['documento', 'profesion'], 'string',
                'length' => [5,300],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 300 caracteres'],
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
            'escuela' => 'Escuela',
            'no_nivel' => 'No Nivel',
            'documento' => 'Documento',
            'profesion' => 'Profesion',
            'fecha_ini' => 'Fecha Ini',
            'fecha_fin' => 'Fecha Fin',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public static function curriculumFields()
    {
        return [
            'escuela' => 'Escuela', 'documento' => 'Documento', 'fecha_ini' => 'Fecha Inicio', 'fecha_fin' => 'Fecha Termnio'
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

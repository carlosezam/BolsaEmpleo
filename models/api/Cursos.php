<?php

namespace app\models\api;

use Yii;

/**
 * This is the model class for table "cursos".
 *
 * @property integer $id
 * @property string $nombre
 * @property string $descripcion
 * @property integer $horas
 * @property string $fecha_ini
 * @property string $fecha_fin
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Cursos extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'cursos';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [
                ['nombre', 'descripcion', 'horas', 'fecha_ini', 'fecha_fin', 'id_usuario'],
                'required', 'message' => 'Campo requerido'
            ],
            [
                ['horas'],
                'integer', 'min' => '1', 'tooSmall' => 'Las horas deben ser mayor a cero'
            ],
            [
                ['fecha_ini', 'fecha_fin'],
                'date', 'format' => 'yyyy-MM-dd','message' => 'Formato de fecha no valido'
            ],
            [['nombre'], 'string', 'max' => 150],
            [['descripcion'], 'string', 'max' => 300],
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
            'descripcion' => 'Descripcion',
            'horas' => 'Horas',
            'fecha_ini' => 'Fecha Ini',
            'fecha_fin' => 'Fecha Fin',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public static function curriculumFields()
    {
        return [
            'nombre' => 'Curso',
            'descripcion' => 'Descripción',
            'horas' => 'No horas',
            'fecha_ini' => 'Fecha Inicio',
            'fecha_fin' => 'Fecha Termino'
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

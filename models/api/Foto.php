<?php
/**
 * Created by PhpStorm.
 * User: Ezam
 * Date: 27/11/2017
 * Time: 02:33 PM
 */

namespace app\models\api;

use yii\base\Model;
use yii\web\UploadedFile;


class Foto extends Model
{
    public $imageFile;

    public function rules()
    {
        return [
            [['imageFile'], 'file', 'skipOnEmpty' => false, 'extensions' => 'png, jpg'],
        ];
    }

    public function upload()
    {
        var_dump( $this->imageFile );
        if($this->validate()){
            $this->imageFile->saveAs(
                'fotos/'.
                $this->imageFile->baseName.
                '.'.
                $this->imageFile->extension);

            return true;
        } else {
            return false;
        }
    }
}
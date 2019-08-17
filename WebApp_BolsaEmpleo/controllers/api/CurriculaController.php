<?php

namespace app\controllers\api;
use app\models\Empleo;
use YII;
use yii\db\ActiveRecord;
use yii\db\Query;
use yii\filters\VerbFilter;
use \app\models\api;
use yii\web\UploadedFile;

class CurriculaController extends \yii\rest\Controller
{
    private $tablas = ['saves','empleos','personal','cursos','competencias','lenguajes','trabajos','escolares','foto'];
    private $tabla = "";

    public function actionUpload()
    {

        $tmp_name   = $_FILES['imageFile']['tmp_name'];
        $id_usuario = YII::$app->request->post('id_usuario');

        $explode = explode('.',$_FILES['imageFile']['name']);
        $extension = end( $explode );

        $target     = 'fotos/' . $id_usuario . '.' .$extension;


        if( $_FILES['imageFile']['error'] > 0 )
        {
            return $this->send_upload_error( $_FILES['imageFile']['error']);
        } else {
            move_uploaded_file( $tmp_name, $target );
            return $this->send_ok_request(null);
        }

    }

    public function actionEmpleos()
    {
        $request    = Yii::$app->request;
        $key_search = $request->post('key_search');
        $id_usuario = YII::$app->request->post('id_usuario');

        $subQuery =
            (new Query())->select('id_empleo')
                        ->from('saves')
                        ->where(['id_usuario'=>$id_usuario]);

        $query =
            (new Query())->from('empleos')
                        ->where(['not in', 'id', $subQuery])
                        ->andWhere(['active'=>1]);

        if( !empty($key_search))
        {
            $query->filterWhere(['like','puesto', $key_search] );
            $query->orFilterWhere(['like','descripcion', $key_search] );
        }



        $data = $query->all();

        if (!$data)
        {
            return $this->send_ok_request( [] );
        }

        return $this->send_ok_request( $data );
    }
    public function actionCreate()
    {
        $request    = Yii::$app->request;

        $model = $this->get_model( $this->tabla );
        $model->attributes = $request->post();

        if( !$model->validate() )
        {
            return $this->send_invalid_data( $model->firstErrors );
        }

        if( !$model->insert() ){
            return $this->send_server_error( $model->getErrors() );
        }

        return $this->send_create_request( $model );
    }

    public function actionRead()
    {
        $request    = Yii::$app->request;

        $model = $this->get_model( $this->tabla );

        $id_usuario = $request->post('id_usuario');

        $data = $model::findAll( ['id_usuario' => $id_usuario] );


        if (!$data)
        {
            return $this->send_ok_request( [] );
        }

        return $this->send_ok_request( $data );

    }

    public function actionView()
    {
        $request    = Yii::$app->request;

        $model = $this->get_model( $this->tabla );

        $data = $model::findOne( $request->post('id') );


        if (!$data)
        {
            return $this->send_ok_request( [] );
        }

        return $this->send_ok_request( $data );

    }

    public function actionUpdate()
    {
        $request    = Yii::$app->request;

        $_model = $this->get_model( $this->tabla );

        $model = $_model::findOne( $request->post('id') );

        if(!$model){
            return $this->send_bad_request('no se encontró el registro con el id especificado');
        }

        $model->attributes = $request->post();

        if( !$model->validate() )
        {
            return $this->send_invalid_data( $model->firstErrors );
        }

        if( !$model->update() ){
            return $this->send_server_error( $model->getErrors() );
        }

        return $this->send_ok_request( $model );


    }

    public function actionDelete()
    {
        $request    = Yii::$app->request;

        $_model = $this->get_model( $this->tabla );

        $model = $_model::findOne( $request->post('id') );

        if(!$model){
            return $this->send_bad_request('no se encontró el registro con el id especificado');
        }

        $model->delete();

    }

    private  function get_model( $name )
    {
        $model = NULL;
        switch ( $name )
        {
            case 'saves': $model = new \app\models\api\Saves(); break;
            case 'personal': $model = new \app\models\api\Persona(); break;
            case 'cursos': $model = new \app\models\api\Cursos(); break;
            case 'competencias': $model = new \app\models\api\Competencias(); break;
            case 'lenguajes': $model = new \app\models\api\Lenguajes(); break;
            case 'trabajos': $model = new \app\models\api\Trabajos(); break;
            case 'escolares': $model = new \app\models\api\Escolares(); break;
            case 'empleos': $model = new \app\models\api\Empleos(); break;
        }

        return $model;
    }

    public function beforeAction($action)
    {
        if( ! parent::beforeAction($action) ) return false;

        $request    = Yii::$app->request;



        if ( !$request->isPost || !$request->post('tabla') )
        {
            $this->send_bad_request('falta el campo tabla');
            return false;
        }

        $tabla = $request->post('tabla');

        if( !in_array($tabla, array_values( $this->tablas ) ) )
        {
            $this->send_bad_request('la tabla no existe o no está disponile');
            return false;
        }

        $this->tabla = $tabla;


        if( in_array( $this->action->id, ['create','read','empleos']))
        {
            if ( !$request->post('id_usuario') )
            {
                $this->send_bad_request('falta el campo id_usuario');
                return false;
            }
        }


        if( in_array( $this->action->id, ['view','update','delete']))
        {
            if ( !$request->post('id') )
            {
                $this->send_bad_request('falta el campo id');
                return false;
            }
        }

        return true;
    }

    private function send_ok_request( $data = null)
    {
        
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 200;
        $response->data = $data;
        return $response->send();
    }

    private function send_create_request( $data )
    {
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 201;
        $response->data = $data;
        return $response->send();
    }

    private function send_invalid_data( $errors )
    {
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 422;
        $response->statusText = 'Data Validation Failed';
        $response->data = $errors;
        return $response->send();
    }
    private function send_server_error( $errors )
    {
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 500;
        //$response->statusText = 'Data Validation Failed';
        $response->data = $errors;
        return $response->send();
    }

    private function send_upload_error( $error_code )
    {
        $message = "";
        switch ($error_code) {
            case UPLOAD_ERR_INI_SIZE:
                $message = "El archivo es más grande que lo permitido por el Servidor.";
            case UPLOAD_ERR_FORM_SIZE:
                $message = "El archivo subido es demasiado grande.";
            case UPLOAD_ERR_PARTIAL:
                $message = "El archivo subido no se terminó de cargar (probablemente cancelado por el usuario).";
            case UPLOAD_ERR_NO_FILE:
                $message = "No se subió ningún archivo";
            case UPLOAD_ERR_NO_TMP_DIR:
                $message = "Error del servidor: Falta el directorio temporal.";
            case UPLOAD_ERR_CANT_WRITE:
                $message = "Error del servidor: Error de escritura en disco";
            case UPLOAD_ERR_EXTENSION:
                $message = "Error del servidor: Subida detenida por la extención";
            default:
                $message = "Error del servidor: ".$error_code;
        }

        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 500;
        $response->statusText = $message;
        $response->data = ['upload' => $message];
        return $response->send();
    }

    private function send_bad_request( $message )
    {
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 400;
        $response->data = ['message' => $message ];
        return $response->send();
    }

    private function html_table( $fields, $data )
    {

        $thead = $this->html_thead( $fields );

        $tbody = '<tbody>';
        $rows = "";

        foreach ( $data as $datakey => $datavalue)
        {
            $tbody .= '<tr>';

            foreach ( $fields as $key => $value )
            {
                $tbody .= '<td>'. $datavalue[ $key ] .'</td>';
            }
            $tbody .= '</tr>';

        }
        return "<table width='90%' align='center'>{$thead}{$tbody}</table>";

    }

    private function html_thead( $headers = array())
    {
        $html = '<thead><tr>';
        foreach ( $headers as $header )
        {
            $html .= '<th>' . $header . '</th>';
        }
        return $html . '</tr></thead>';
    }
}

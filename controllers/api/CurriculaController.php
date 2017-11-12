<?php

namespace app\controllers\api;
use YII;
use yii\db\ActiveRecord;
use yii\filters\VerbFilter;
use \app\models\api;

class CurriculaController extends \yii\rest\Controller
{
    private $tablas = ['personal' ];
    private $tabla = "";


    public function actionCreate()
    {
        $request    = Yii::$app->request;

        $model = $this->get_model( $this->tabla );
        $model->attributes = $request->post();

        if( !$model->validate() )
        {
            return $this->send_invalid_data( $model->errors );
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
            return $this->send_invalid_data( $model->errors );
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
            case 'personal': $model = new \app\models\api\Persona();
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


        if( in_array( $this->action->id, ['create','read']))
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

    private function send_ok_request( $data )
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

    private function send_bad_request( $message )
    {
        $response = YII::$app->response;

        $response->format = \yii\web\Response::FORMAT_JSON;
        $response->statusCode = 400;
        $response->data = ['message' => $message ];
        return $response->send();
    }
}

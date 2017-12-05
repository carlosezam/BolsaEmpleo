<?php

namespace app\controllers;

use app\models\api\Competencias;
use app\models\api\Cursos;
use app\models\api\Escolares;
use app\models\api\Lenguajes;
use app\models\api\Persona;
use app\models\api\Trabajos;
use Codeception\Lib\Interfaces\ActiveRecord;
use Yii;
use mPDF;
use yii\helpers\Url;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\Response;
use yii\filters\VerbFilter;
use app\models\LoginForm;
use app\models\ContactForm;
use yii\widgets\ActiveForm;

class SiteController extends Controller
{
    /**
     * @inheritdoc
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['logout','contact','about'],
                'rules' => [
                    [
                        'actions' => ['index','login','signup'],
                        'allow' => true,
                        'roles' => ['?'],
                    ],
                    [
                        'actions' => ['logout','contact','about'],
                        'allow' => true,
                        'roles' => ['@']
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'logout' => ['post','get'],
                ],
            ],
        ];
    }

    /**
     * @inheritdoc
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => 'yii\web\ErrorAction',
            ],
            'captcha' => [
                'class' => 'yii\captcha\CaptchaAction',
                'fixedVerifyCode' => YII_ENV_TEST ? 'testme' : null,
            ],
        ];
    }

    public function actionFormulario( $mensaje = NULL ) {
        return $this->render('formulario', ['mensaje'=>$mensaje]);
    }
    
    public function actionRequest() {
        $mensaje = NULL;
        if(isset($_REQUEST['nombre']))
        {
            $mensaje = 'Nombre recibido: '.$_REQUEST['nombre'];
        }
        $this->redirect(['site/formulario', 'mensaje'=>$mensaje]);
    }

    /**
     * Displays homepage.
     *
     * @return string
     */
    public function actionIndex()
    {
        return $this->redirect('empresa/index');
        //return $this->render('index');
    }

    public function actionPdf( $id_usuario = 0)
    {

        $image = Url::to('@web/fotos/'.$id_usuario.'.jpg',true);

        $data = Persona::findOne(['id_usuario'=>$id_usuario]);
        $mpdf = new mPDF;

        $html = "<h1 align='center'><i>Curriculum</i></h1>";

        $html .= '<table width="90%" align="center" border=1 cellspacing=0 cellpadding=2 bordercolor="666633"><tbody>'.
            "<tr>
                <td><b>Curp</b></td><td>". strtoupper( $data->curp) ."</td>
                <td rowspan='4' align='center'><img width='100px' height='100px' src='{$image}'></td>
            </tr>".

            "<tr> <td><b>Nombre</b></td> <td>{$data->nombres}</td> </tr>".
            "<tr> <td><b>Apellido Paterno</b></td> <td>{$data->ape_pat}</td> </tr>".
            "<tr> <td><b>Apellido Materno</b></td> <td>{$data->ape_mat}</td> </tr>".

            "<tr><td colspan='3'><table width='100%' >
                  <tr >
                    <td><b>Fecha Nacimiento</b></td><td>{$data->fecha_nac}</td>
                    <td><b>Sexo</b></td><td>". $data->sexo ."</td>
                    <td><b>Edo Civil</b></td><td>{$data->edo_civil}</td>
                  </tr>
            </table></td></tr>".

            "<tr><td colspan='3'><table width='100%' >
                  <tr>
                    <td ><b>Telefono</b></td><td>{$data->telefono}</td>
                    <td><b>Licencia</b></td><td>". ($data->licencia ? 'si' : 'no')."</td></tr>
            </table></td></tr>".

            "<tr style='border: solid 2px black'><td colspan='3'><table width='100%'>
                  <tr >
                    <td style='border-right: 2px solid black'><b>Domicilio</b></td><td>{$data->domicilio}</td>
            </table></td></tr>".


            '</tbody></table>';




        $data = Lenguajes::findAll(['id_usuario'=>$id_usuario]);
        if( count($data) > 0 ) {
            $html .= '<br/>';
            $html .= "<h3 align='center'>Idiomas</h3>";
            $html .= $this->html_table(Lenguajes::curriculumFields(), $data);
        }

        $data = Escolares::findAll(['id_usuario'=>$id_usuario]);
        if( count($data) > 0 ) {
            $html .= '<br/>';
            $html .= "<h3 align='center'>Formación</h3>";
            $html .= $this->html_table(Escolares::curriculumFields(), $data);
        }

        $data = Competencias::findAll(['id_usuario'=>$id_usuario]);
        if ( count($data) > 0 ) {
            $html .= '<br/>';
            $html .= "<h3 align='center'>Competencias</h3>";
            $html .= $this->html_table(Competencias::curriculumFields(), $data);
        }

        $data = Cursos::findAll(['id_usuario'=>$id_usuario]);
        if ( count($data) > 0 ) {
            $html .= '<br/>';
            $html .= "<h3 align='center'>Cursos</h3>";
            $html .= $this->html_table(Cursos::curriculumFields(), $data);
        }

        $data = Trabajos::findAll(['id_usuario'=>$id_usuario]);
        if ( count($data) > 0 )
        {
            $html .= '<br/>';
            $html .= "<h3 align='center'>Experiencia Laboral</h3>";
            $html .= $this->html_table( Trabajos::curriculumFields(), $data );
        }
        $mpdf->WriteHTML($html);
        $mpdf->Output();
        exit;
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
        return "<table width='90%' align='center' >{$thead}{$tbody}</table>";

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

    public function actionSignup()
    {
        $message = NULL;

        $model = new \app\models\SignupForm();
        if($model->load(Yii::$app->request->post()) && Yii::$app->request->isAjax)
        {

            Yii::$app->response->format = Response::FORMAT_JSON;
            return ActiveForm::validate($model);
        }


        if($model->load(Yii::$app->request->post()) && $model->signup() )
        {
            $this->sendConfirmEmail($model->email);
            return $this->redirect('login');
        }

        return $this->render('signup',['model'=>$model,'message'=>$message]);
    }

    public function sendConfirmEmail($email=null)
    {
        if( $email === null ) $this->redirect('signup');

        $usuario = \app\models\Usuario::findByUsernameOrEmail( $email );

        $id = urlencode($email);
        $token = $usuario->accessToken;

        $subject = "Confirmar registro";
        $body = "<h1>Haga click en el siguiente enlace para finalizar tu registro</h1>";
        $body .= "<a href='site/confirm?email=".$id."&token=".$token."'>Confirmar</a>";

        //Enviamos el correo
        Yii::$app->mailer->compose()
            ->setTo($email)
            ->setFrom([Yii::$app->params["adminEmail"] => Yii::$app->params["title"]])
            ->setSubject($subject)
            ->setHtmlBody($body)
            ->send();
    }

    public function actionConfirmEmail($email=null, $token = null)
    {
        var_dump($email);
        var_dump($token);
    }
    /**
     * Login action.
     *
     * @return Response|string
     */
    public function actionLogin()
    {
        if (!Yii::$app->user->isGuest) {
            return $this->goHome();
        }

        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {
            return $this->goBack();
        }
        return $this->render('login', [
            'model' => $model,
        ]);
    }


    /**
     * Logout action.
     *
     * @return Response
     */
    public function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }

    /**
     * Displays contact page.
     *
     * @return Response|string
     */
    public function actionContact()
    {
        $model = new ContactForm();
        if ($model->load(Yii::$app->request->post()) && $model->contact(Yii::$app->params['adminEmail'])) {
            Yii::$app->session->setFlash('contactFormSubmitted');

            return $this->refresh();
        }
        return $this->render('contact', [
            'model' => $model,
        ]);
    }

}

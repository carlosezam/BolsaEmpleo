<?php

namespace app\controllers\api;

//use Yii;
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
                    'logout' => [],
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
        echo "hola";
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

<?php 
namespace app\commands;

use Yii;
use yii\console\Controller;

class RbacController extends Controller
{
	public function actionInit()
	{
		$auth = Yii::$app->authManager;

		
		$createEmpresa = $auth->createPermission('createEmpresa');
		$createEmpresa->description = 'Registrar una empresa';
		$auth->add($createEmpresa);

		$manageEmpresa = $auth->createPermission('manageEmpresa');
		$manageEmpresa->description = 'Administar datos de una empresa';
		$auth->add($manageEmpresa);

		$manageEmpleo = $auth->createPermission('manageEmpleo');
		$manageEmpleo->description = 'Administar datos de una empleo';
		$auth->add($manageEmpleo);

		$managePerson = $auth->createPermission('managePerson');
		$managePerson->description = 'Administar datos personales';
		$auth->add($managePerson);

		$empresa = $auth->createRole('empresa');
		$auth->add($empresa);
		$auth->addChild($empresa,$manageEmpresa);
		$auth->addChild($empresa,$manageEmpleo);

		$person = $auth->createRole('person');
		$auth->add($person);
		$auth->addChild($person,$managePerson);

		$admin = $auth->createRole('admin');
		$auth->add($admin);
		$auth->addChild($admin,$createEmpresa);
		$auth->addChild($admin,$empresa);
		$auth->addChild($admin,$person);

		$auth->assign($admin,100);
		$auth->assign($empresa,101);
	}
}


 ?>
$.application.config(function($stateProvider, $urlRouterProvider)
{
	//
	// For any unmatched url, redirect to /state1
	$urlRouterProvider.otherwise("default");
	
	var addState = function(name, menuUrl, contentUrl, tabName, hideLeftMenu, projectSpecific) {
		var url = "/" + name;
		
		var stateConfig = {
		    "url" : url,
		    "views" :
		    {
		        "menuView" :
		        {
		        	"templateUrl" : "/ui/html/" + menuUrl
		        },
		        "mainContent":
		        {
		        	"templateUrl" : "/ui/html/" + contentUrl
		        }
		    },
		    "tab": tabName,
		    "leftMenu": hideLeftMenu == true ? false : true,
		    "projectSpecific": projectSpecific == false ? false : true
		};
		
		$stateProvider.state(name, stateConfig);
	};
	
	//Home related states
	addState("default", "common/common-menu.html", "common/dashboard.html", "homeTab");
	
	addState("homeCalDayView", "common/common-menu.html", "common/cal-day-view.html", "homeTab");
	addState("homeCalWeekView", "common/common-menu.html", "common/cal-week-view.html", "homeTab");
	addState("homeCalMonthView", "common/common-menu.html", "common/cal-month-view.html", "homeTab");
	
	addState("homeMailInbox", "common/common-menu.html", "common/mails.html", "homeTab");
	//addState("homeMailInbox", "common/common-menu.html", "common/mail-inbox.html", "homeTab");
	addState("homeMailSent", "common/common-menu.html", "common/mail-sent.html", "homeTab");
	addState("homeMailTrash", "common/common-menu.html", "common/mail-trash.html", "homeTab");
	  
	addState("homeNotes", "common/common-menu.html", "common/notes.html", "homeTab");
	addState("homeContacts", "common/common-menu.html", "common/contacts.html", "homeTab");
	addState("homeAlerts", "common/common-menu.html", "common/alerts.html", "homeTab");
	addState("homeNotifications", "common/common-menu.html", "common/notifications.html", "homeTab");
	addState("homeWidgets", "common/common-menu.html", "common/widgets.html", "homeTab");
	
	
	//Customer related states
	addState("customers", "customer/customer-menu.html", "customer/customers.html", "customersTab");
	addState("customerPayments", "customer/customer-menu.html", "customer/payments.html", "customersTab");
	addState("invoice", "customer/customer-menu.html", "customer/invoice.html", "customersTab");

	//Settings related states
	addState("extensions", "settings/settings-menu.html", "settings/extensions.html", "settingsTab");
	addState("adminSettings", "settings/settings-menu.html", "settings/settings.html", "settingsTab");
	addState("pricePlans", "settings/settings-menu.html", "settings/price-plans.html", "settingsTab");
	addState("adminUser", "settings/settings-menu.html", "settings/adminuser.html", "settingsTab");
	addState("mailSettings", "settings/settings-menu.html", "settings/mailSetting.html", "settingsTab");
	addState("notificationSettings", "settings/settings-menu.html", "settings/notificationSettings.html", "settingsTab");
	addState("mailTemplateDefinition", "settings/settings-menu.html", "settings/mailTemplateDefinition.html", "settingsTab");
	addState("holiday", "settings/settings-menu.html", "settings/holiday.html", "settingsTab");
	
	//Resources related states
	addState("employee", "resources/resources-menu.html", "resources/employee.html", "resourcesTab");
	addState("designation","resources/resources-menu.html", "resources/designation.html", "resourcesTab");
	
	// Project related states
	addState("project","project/project-menu.html", "project/project.html", "projectsTab");
	addState("release","project/project-menu.html", "project/release.html", "projectsTab", false, false);
	addState("sprint","project/project-menu.html", "project/sprint.html", "projectTab");
	
	//Members related states
	addState("member", "member/member-menu.html", "member/member.html", "membersTab");
	
	//backlog
	addState("kanbanBoard","story/story-menu.html", "kanban-board/kanban-board.html", "kanbanTab", true);
	addState("story","story/story-menu.html", "story/story.html", "storyTab");
	addState("tag","story/story-menu.html", "story/tag.html", "storyTab");
	addState("task","story/story-menu.html", "story/task-header.html", "storyTab");
	
	//bug
	addState("bug","bug/bug-menu.html", "bug/bug.html", "bugTab");
	
	//scrum
	addState("scrum","scrum/scrum-menu.html", "scrum/scrum-meeting.html", "scrumTab", true);
	
	//pokerGame
	addState("pokerGame","poker-game/pokerGame-menu.html", "poker-game/poker-game.html", "pokerGameTab" ,true);
	
});

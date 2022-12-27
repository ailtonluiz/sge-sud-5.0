var AL = AL || {};

AL.onSidebarToggleRequest = function(event) {
  event.preventDefault();
  $(this).blur();

  $('.js-sidebar, .js-content').toggleClass('is-toggled');
};

AL.onSearchModalShowRequest = function(event) {
  event.preventDefault();

  $('.js-search-modal').fadeIn('slow');
  $('body').addClass('al-no-scroll');
  
  $('.js-search-modal-input').val('').select();
  
};

AL.onSearchModalCloseRequest = function(event) {
  event.preventDefault();

  $('.js-search-modal').hide();
  $('body').removeClass('al-no-scroll');
};

//AL.onFormLoadingSubmit = function(event) {
  //event.preventDefault();

  //AL.showLoadingComponent();

  //setTimeout(function() {
  //  AL.hideLoadingComponent();
  //}, 2000);
//};

AL.showLoadingComponent = function() {
  $('.js-loading-overlay').css('display', 'table').hide().fadeIn('slow');
};

AL.hideLoadingComponent = function() {
  $('.js-loading-component').fadeOut('fast');
};

AL.initStickyTableHeaders = function() {
  if ($(window).width() >= 992) { 
    var stickyRef = $('.js-sticky-reference');
    var stickyTable = $('.js-sticky-table');

    if (stickyRef && stickyTable) {
      stickyTable.stickyTableHeaders({fixedOffset: stickyRef});
    }
  }
};

AL.onMenuGroupClick = function(event) {
  var subItems = $(this).parent().find('ul');

  if (subItems.length) {
    event.preventDefault();
    $(this).parent().toggleClass('is-expanded');
  }
};

AL.initMenu = function() {
  $('.js-menu > ul > li > a').bind('click', AL.onMenuGroupClick);
  $('.al-menu__item .is-active').parents('.al-menu__item').addClass('is-expanded is-active');
};

$(function() {
  if (AL.init) {
    AL.init();
  }

  AL.initMenu();
  AL.initStickyTableHeaders();
  
  // Enable Bootstrap tooltip
  $('.js-tooltip').tooltip();
  
  // Bind events
  $('.js-sidebar-toggle').bind('click', AL.onSidebarToggleRequest);
  $('.js-search-modal-trigger-show').bind('click', AL.onSearchModalShowRequest);
  $('.js-search-modal-close').bind('click', AL.onSearchModalCloseRequest);
  //$('.js-form-loading').bind('submit', AL.onFormLoadingSubmit);
});
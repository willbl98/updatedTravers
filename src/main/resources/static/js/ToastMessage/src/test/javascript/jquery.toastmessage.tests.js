/*
 * Travers Consulting
 *
 * [2014] - [2019] Travers Consulting Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Travers Consulting Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Travers Consulting Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Travers Consulting Incorporated.
 */

describe('the Toastmessage jQuery Plugin',function(){

	// Remove any exisiting toastmessage container
	beforeEach(function() {
        $('.toast-container').remove();
        this.addMatchers({
            toastContainterCreated : function() { return $('.toast-container').length > 0; }
        });

    });

	//Clean it up after each spec
	afterEach(function() {
        $('.toast-container').remove();
	});

	//Specs
	describe('showing toastmessages',function() {
		it('shows a success toastmessage',function(){

            // call to our jquery plugin under test
            $().toastmessage('showSuccessToast', "SUCCESS");

			expect().toastContainterCreated();
			expect($('.toast-item.toast-type-success').length > 0).toBeTruthy();
		});
		it('shows a notice toastmessage',function(){
            // call to our jquery plugin under test
            $().toastmessage('showNoticeToast', "NOTICE");

            expect().toastContainterCreated();
			expect($('.toast-item.toast-type-notice').length > 0).toBeTruthy();
		});
		it('shows a warning toastmessage',function(){
            // call to our jquery plugin under test
            $().toastmessage('showWarningToast', "WARNING");

			expect().toastContainterCreated();
			expect($('.toast-item.toast-type-warning').length > 0).toBeTruthy();
		});
		it('shows a error toastmessage',function(){
            // call to our jquery plugin under test
            $().toastmessage('showErrorToast', "ERROR");

			expect().toastContainterCreated();
			expect($('.toast-item.toast-type-error').length > 0).toBeTruthy();
		});
		it('shows a user configured toastmessage',function(){
            // call to our jquery plugin under test
            $().toastmessage({
                sticky   : true,
                position : 'top-right',
                closeText: ''
            });
            $().toastmessage('showToast', {
                text     : 'Success Dialog',
                type     : 'success'
            });

			expect().toastContainterCreated();
			expect($('.toast-item.toast-type-success').length > 0).toBeTruthy();
		});
	});


    describe('removing toastmessages',function() {
        it('remove the toast with a mouseclick',function(){

            var callback = jasmine.createSpy('Close-Callback');

            // call to our jquery plugin under test
            $().toastmessage('showToast', {
                text     : 'Success Dialog',
                sticky   : true,
                position : 'top-right',
                type     : 'success',
                closeText: '',
                close    : callback
            });

            expect(callback).not.toHaveBeenCalled();

            // closing the dialog manually
            $('.toast-item-close').click();

            expect(callback).toHaveBeenCalled();
        });
        it('remove the toast via api call',function(){

            var callback = jasmine.createSpy('Close-Callback');

            // call to our jquery plugin under test
            var toast = $().toastmessage('showToast', {
                text     : 'Success Dialog',
                sticky   : true,
                position : 'top-right',
                type     : 'success',
                closeText: '',
                close    : callback
            });

            expect(callback).not.toHaveBeenCalled();

            // closing the dialog manually
            $().toastmessage('removeToast', toast, { close : callback});

            expect(callback).toHaveBeenCalled();
        });
    });

});
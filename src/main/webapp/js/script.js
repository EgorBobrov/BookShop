var lang = navigator.language

console.log(lang);

function ValidateBookAdditionForm() {
	var desc_input = document.getElementById("desc");
	var title_input = document.getElementById("ttl");
	var cover_input = document.getElementById("cvr");
	var isbn_input = document.getElementById("isbn");
	var nbOfPages_input = document.getElementById("nbOfPages");
	var authors_input = document.getElementById("authors");
	var price_input = document.getElementById("price");
	var discount_input = document.getElementById("discount");
	var amountInStock_input = document.getElementById("amountInStock");
	var valid = true;

	if (desc_input.value == "") {
		var message = document.getElementById("desc_validation");
		message.innerHTML = "Please don't live book description empty";
		message.style.display = "";
		valid = false;
	}
	if (title_input.value == "") {
		var message = document.getElementById("ttl_validation");
		message.innerHTML = "Please provide book title";
		message.style.display = "";
		valid = false;
	}
	if (isbn_input.value == "") {
		var message = document.getElementById("isbn_validation");
		message.innerHTML = "Please provide book ISBN";
		message.style.display = "";
		valid = false;
	}
	// no idea why, but in this case == "" check doesn't work
	// commented out for future revision
/*	if (cover_input.value.length <= 0) {
		var message = document.getElementById("cvr_validation");
		message.innerHTML = "Please provide cover picture";
		message.style.display = "";
		valid = false;
	}
	*/	
	
	if (nbOfPages_input.value == "" || isNaN(nbOfPages_input.value)) {
		var message = document.getElementById("nbOfPages_validation");
		message.innerHTML = "Please provide number of pages in numeric format";
		message.style.display = "";
		valid = false;
	}
	if (authors_input.value == "") {
		var message = document.getElementById("authors_validation");
		message.innerHTML = "Please list at least one author";
		message.style.display = "";
		valid = false;
	}
	if (price_input.value == "" || isNaN(price_input.value)) {
		var message = document.getElementById("price_validation");
		message.innerHTML = "Please enter price of the book in numeric format";
		message.style.display = "";
		valid = false;
	}
	if (discount_input.value == "" || isNaN(discount_input.value)) {
		var message = document.getElementById("discount_validation");
		message.innerHTML = "Please enter discount value in numeric format";
		message.style.display = "";
		valid = false;
	}
	if (amountInStock_input.value == ""
		|| isNaN(amountInStock_input.value)) {
		var message = document
		.getElementById("amountInStock_validation");
		message.innerHTML = "Please enter amount of available books in numeric format";
		message.style.display = "";
		valid = false;
	}

	return valid;
}

function validateComment() {
	var valid = true;
	var comment_input = document.getElementById("comm");
	if (comment_input.value.length <= 0) {
		var message = document
		.getElementById("comment_validation");
		message.innerHTML = "Comment text should not be empty.";
		message.style.display = "";
		valid = false;
	}
	return valid;
}

function validateCheckout() {
	var valid = true;
	var card_input = document.getElementById("cardNum");
	var trimmed_input = trim(card_input.value);
	
	var year_input = document.getElementById("year");
	var month_input = document.getElementById("month");
	
	var cvc_input = document.getElementById("cvc");
	if (trimmed_input.length != 16 || isNaN(trimmed_input)) {
		var message = document
		.getElementById("cardNum_validation");
		message.innerHTML = "Please enter card number in 16-digit format without.";
		message.style.display = "";
		valid = false;
	}
	
	if ((year_input.value.length <= 0 || isNaN(year_input.value)) && ((month_input.value.length <= 0 || isNaN(month_input.value)))) {
		var message = document
		.getElementById("expDate_validation");
		message.innerHTML = "Please enter expiration date (month and year) in two-digit format.";
		message.style.display = "";
		valid = false;
	}
	
	if (cvc_input.value.length != 3 || isNaN(cvc_input.value)) {
		var message = document
		.getElementById("cvc_validation");
		message.innerHTML = "Please enter correct CVC value in 3-digit format.";
		message.style.display = "";
		valid = false;
	}

	return valid;
}

function trim(value) {
    return value.replace(/ /g,"");
}


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
		message.innerHTML = "Пожалуйста, введите описание книги";
		message.style.display = "";
		valid = false;
	}
	if (title_input.value == "") {
		var message = document.getElementById("ttl_validation");
		message.innerHTML = "Пожалуйста, введите название книги";
		message.style.display = "";
		valid = false;
	}
	if (isbn_input.value == "") {
		var message = document.getElementById("isbn_validation");
		message.innerHTML = "Пожалуйста, введите ISBN";
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
		message.innerHTML = "Пожалуйста, введите число страниц в числовом формате";
		message.style.display = "";
		valid = false;
	}
	if (authors_input.value == "") {
		var message = document.getElementById("authors_validation");
		message.innerHTML = "Пожалуйста, укажите как минимум одного автора";
		message.style.display = "";
		valid = false;
	}
	if (price_input.value == "" || isNaN(price_input.value)) {
		var message = document.getElementById("price_validation");
		message.innerHTML = "Пожалуйста, укажите цену книги в числовом формате";
		message.style.display = "";
		valid = false;
	}
	if (discount_input.value == "" || isNaN(discount_input.value)) {
		var message = document.getElementById("discount_validation");
		message.innerHTML = "Пожалуйста, укажите значение скидки в числовом формате (или 0)";
		message.style.display = "";
		valid = false;
	}
	if (amountInStock_input.value == ""
		|| isNaN(amountInStock_input.value)) {
		var message = document
		.getElementById("amountInStock_validation");
		message.innerHTML = "Пожалуйста, укажите число имеющихся на складе книг в числовом формате";
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
		message.innerHTML = "Пожалуйста, введите текст комментария";
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
		message.innerHTML = "Пожалуйста, введите номер банковской карты в виде 16 цифр";
		message.style.display = "";
		valid = false;
	}
	
	if ((year_input.value.length <= 0 || isNaN(year_input.value)) && ((month_input.value.length <= 0 || isNaN(month_input.value)))) {
		var message = document
		.getElementById("expDate_validation");
		message.innerHTML = "Пожалуйста, введите дату (месяц и год), до которой действует карта, в формате 2 цифр";
		message.style.display = "";
		valid = false;
	}
	
	if (cvc_input.value.length != 3 || isNaN(cvc_input.value)) {
		var message = document
		.getElementById("cvc_validation");
		message.innerHTML = "Пожалуйста, введите CVC в формате 3 цифр";
		message.style.display = "";
		valid = false;
	}

	return valid;
}

function trim(value) {
    return value.replace(/ /g,"");
}

function validateAddress() {
	var valid=true;
	
	var name_input = document.getElementById("name");
	var street_input = document.getElementById("street");
	var city_input = document.getElementById("city");
	var region_input = document.getElementById("region");
	var country_input = document.getElementById("country");

	if (name_input.value == "") {
		var message = document.getElementById("name_validation");
		message.innerHTML = "Пожалуйста, введите своё имя";
		message.style.display = "";
		valid = false;
	}
	
	if (street_input.value == "") {
		var message = document.getElementById("street_validation");
		message.innerHTML = "Пожалуйста, укажите улицу проживания";
		message.style.display = "";
		valid = false;
	}
	if (city_input.value == "") {
		var message = document.getElementById("city_validation");
		message.innerHTML = "Пожалуйста, укажите город проживания";
		message.style.display = "";
		valid = false;
	}
	if (region_input.value == "") {
		var message = document.getElementById("region_validation");
		message.innerHTML = "Пожалуйста, укажите свой регион";
		message.style.display = "";
		valid = false;
	}
	if (country_input.value == "") {
		var message = document.getElementById("country_validation");
		message.innerHTML = "Пожалуйста, укажите свою страну";
		message.style.display = "";
		valid = false;
	}

	return valid;
}


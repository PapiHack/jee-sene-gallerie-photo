
		function getPath() {
			var actualPath = window.location.href
			var pathSplitted = actualPath.split('/')
			var length = pathSplitted.length
			var url = pathSplitted[length - 1].includes('add') ? '/user/album/image/add' : '/user/album/image/update'
			return url
		}
		
		var myDropzone = new Dropzone('#myDropzone', {
			url: '/SeneGalleriePhoto' + getPath(),
			method: 'post',
			maxFiles: 1,
			uploadMultiple: true,
			paramName: 'images', // Par défaut Dropzone.js ajoute [] au niveau de ce dernier lorsque uploadMultiple est à true
			acceptedFiles: 'image/*',
			addRemoveLinks: true,
			dictDefaultMessage: 'Clickez dans la zone ou faites un glisser-déposer de vos images !',
			dictFallBackMessage: 'Désolé, votre navigateur ne supporte pas cette fonctionnalité !',
			dictRemoveFile: 'Supprimer cette image',
			dictMaxFilesExceeded: 'Vous ne pouvez uploader qu\'une image à la fois !',
			dictInvalidFileType: 'Le format de cette image est invalide !',
			dictFileTooBig: 'La taille de cette image est trop grande',
			autoProcessQueue: false,
			init: function() {
				/* 
				 * J'implémente ce callback afin de modifier le comportement par défaut lors de la
				 * soumission du formulaire afin d'envoyer en même temps les données des autres
				 * champs du formulaire.
				 * 
				 */
				dzClosure = this
				$('#btnSubmit').on('click', function(event) {
					event.preventDefault()
					event.stopPropagation()
					dzClosure.processQueue()
				})
				//Envoie des données en même temps que les fichiers
		        this.on('sendingmultiple', function(data, xhr, formData) {
		            formData.append('titre', $("#titre").val())
		            formData.append('description', $('#description').val())
		            formData.append('hauteur', $('#hauteur').val())
		            formData.append('largeur', $('#largeur').val())
		            formData.append('albumId', $('#albumId').val())
		            console.log(xhr)
		            var tab = ['vide', 'description', 'titre', 'hauteur', 'largeur']
		            xhr.addEventListener('readystatechange', function() {
		            	if (xhr.readyState == 4 && xhr.status == 200) {
		        			for(el of tab) {
			            		document.getElementById(el + 'Error').style.display = 'none'
		        			}
		        			console.log(xhr.response)
		            		var response = JSON.parse(xhr.response)
		            		console.log(response)
		            		if('errors' in response) {
		            			for(el of tab) {
		            				if(el in response.errors) {
				            			document.getElementById(el + 'Error').style.display = ''
		            				}
		            			}
		            		}
		            		else if('message' in response) {
		            			if(response.message == 'OK') {		            				
		            				//window.location.href = window.location.host + '/SeneGalleriePhoto/user/albums';
		            				window.location.replace('/SeneGalleriePhoto/user/albums')
		            			}
		            			else {
		            				document.getElementById('messageError').textContent = response.message
		            				document.getElementById('er').style.display = ''
		            			}
		            		}
		            	}
		            })
		        })
			}
		})
		

function MultiSelector(fileList) {
    this.fileList = fileList;
    this.count = 1;
    this.id = 1;
    
    this.addFileElement = function(element) {
        
        // Make sure it's a file input element
        if(element.tagName == 'INPUT' && element.type == 'file'){
            
            element.name = 'files[file' + (this.id++)+']';
            element.fileSelector = this;
            
            element.onchange = function() {
                var newFileTag = document.createElement('input');
                newFileTag.type = 'file';
                
                $(this).up().insertBefore(newFileTag, this);
                
                // Apply 'update' to element
                this.fileSelector.addFileElement(newFileTag);
                // Update list
                this.fileSelector.addFileToList(this);
                
                this.style.position = 'absolute';
                this.style.left = '-1000px';
            }
            
            this.count++;
            this.currentFile = element;
        }
    }
    
    this.addFileToList = function(element) {
        var newFile = document.createElement('div');
        
        var newFileDeleteButton = document.createElement('input');
        newFileDeleteButton.type = 'button';
        newFileDeleteButton.value = 'Delete';
        
        // reference to the file element so it can be removed
        newFile.element = element;
        
        newFileDeleteButton.onclick = function() {
            
            // Remove element from form
            $(this).up().element.up().removeChild($(this).up().element);
            
            // Remove this row from the list
            $(this).up().up().removeChild($(this).up());
            $(this).up().element.fileSelector.count--;
            
            return false;
        }
        
        newFile.innerHTML = element.value;
        newFile.appendChild(newFileDeleteButton);
        this.fileList.appendChild(newFile);
    }
}
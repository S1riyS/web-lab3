class Validator {
    static _ALLOWED_R_VALUES = ["1", "2", "3", "4", "5"]
    static _NUMBER_REGEXP = /^-?\d*\.?\d*$/


    static $validateX(x) {
        if (isNaN(x)) throw new Error("Неверное значение X");

        let isNumber = Validator._NUMBER_REGEXP.test(x);
        if (!isNumber) throw new Error("X не является числом");

        let geNegative2 = bigDecimal.compareTo(x, "-2") >= 0;
        let lePositive2 = bigDecimal.compareTo("2", x) >= 0;
        if (!(geNegative2 && lePositive2)) throw new Error("Число X не входит в диапазон [-2; 2]");
    }

    static $validateY(y) {
        if (isNaN(y)) throw new Error("Неверное значение Y");

        let isNumber = Validator._NUMBER_REGEXP.test(y);
        if (!isNumber) throw new Error("Y не является числом");

        let geNegative3 = bigDecimal.compareTo(y, "-3") >= 0;
        let lePositive5 = bigDecimal.compareTo("5", y) >= 0;
        if (!(geNegative3 && lePositive5)) throw new Error("Число Y не входит в диапазон [-3; 5]");
    }

    static $validateR(r) {
        if (isNaN(r)) throw new Error("Неверное значение R");

        let isNumber = Validator._NUMBER_REGEXP.test(r);
        if (!isNumber) throw new Error("R не является числом");

        if (!Validator._ALLOWED_R_VALUES.includes(r)) throw new Error("R не содержится в списке [1, 2, 3, 4, 5]");
    }

    static validateAll(x, y, r) {
        Validator.$validateX(x);
        Validator.$validateY(y);
        Validator.$validateR(r);
    }
}

class GraphService {
    constructor(graphId, formService) {
        this.svg = document.getElementById(graphId);
        this.svg.addEventListener("click", (event) => this.$clickHandler(event))

        this.formService = formService

        this.CENTER_X = 200;
        this.CENTER_Y = 200;
        this.MAX_SVG_R = 130;
    }

    drawPoints(rFilterValue) {
        console.log("drawPoints called");
        // Очищаем старые точки перед отрисовкой новых
        this.svg.querySelectorAll(".data-point").forEach(point => point.remove());

        // Находим все точки в #points-data
        const points = document.querySelectorAll("#points-data .point");

        points.forEach(point => {
            // Извлекаем данные из атрибутов
            const x = parseFloat(point.getAttribute("data-x"));
            const y = parseFloat(point.getAttribute("data-y"));
            const r = parseInt(point.getAttribute("data-r"));
            const color = point.getAttribute("data-color");
            const result = point.getAttribute("data-result") === "true";

            if (r !== rFilterValue) return;

            // Преобразуем координаты для SVG-системы (центр 250,250 и масштаб)
            const svgX = 200 + x * (this.MAX_SVG_R / 5);
            const svgY = 200 - y * (this.MAX_SVG_R / 5);

            // Создаем круг для точки
            const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
            // Size and position
            circle.setAttribute("cx", svgX);
            circle.setAttribute("cy", svgY);
            circle.setAttribute("r", 2);
            // Color
            if (color) circle.setAttribute("fill", color);
            else circle.setAttribute("fill", result ? "green" : "red");

            circle.classList.add("data-point");

            // Добавляем точку на svg
            this.svg.appendChild(circle);
        });
    }

    redrawGraph(r) {
        const currentSvgR = this.MAX_SVG_R * (r / 5);

        // Updating rect
        document.getElementById("rect").setAttribute("width", currentSvgR);
        document.getElementById("rect").setAttribute("height", currentSvgR / 2);

        // Updating triangle
        const A = `${this.CENTER_X},${this.CENTER_Y}`
        const B = `${this.CENTER_X},${this.CENTER_Y - currentSvgR}`
        const C = `${this.CENTER_X + currentSvgR},${this.CENTER_Y}`
        const newTrianglePoints = `${A} ${B} ${C}`
        document.getElementById("triangle").setAttribute("points", newTrianglePoints);

        // Updating arc
        const mValues = `${this.CENTER_X} ${this.CENTER_Y + currentSvgR / 2}`;
        const aValues = `${currentSvgR / 2} ${currentSvgR / 2} 0 0 1 ${this.CENTER_X - currentSvgR / 2} ${this.CENTER_Y}`
        const lValues = `${this.CENTER_X} ${this.CENTER_Y}`
        document.getElementById("arc").setAttribute("d", `M ${mValues} A ${aValues} L ${lValues} Z`);

        // Update other markers and labels (same as before)
        document.getElementById("mark-neg-rx").setAttribute("x1", this.CENTER_X - currentSvgR);
        document.getElementById("mark-neg-rx").setAttribute("x2", this.CENTER_X - currentSvgR);

        document.getElementById("mark-rx").setAttribute("x1", this.CENTER_X + currentSvgR);
        document.getElementById("mark-rx").setAttribute("x2", this.CENTER_X + currentSvgR);

        document.getElementById("mark-ry").setAttribute("y1", this.CENTER_Y - currentSvgR);
        document.getElementById("mark-ry").setAttribute("y2", this.CENTER_Y - currentSvgR);

        document.getElementById("mark-neg-ry").setAttribute("y1", this.CENTER_Y + currentSvgR);
        document.getElementById("mark-neg-ry").setAttribute("y2", this.CENTER_Y + currentSvgR);

        document.getElementById("label-neg-rx").setAttribute("x", this.CENTER_X - (currentSvgR + 3));
        document.getElementById("label-rx").setAttribute("x", this.CENTER_X + (currentSvgR + 3));

        document.getElementById("label-neg-ry").setAttribute("y", this.CENTER_Y + (currentSvgR + 3));
        document.getElementById("label-ry").setAttribute("y", this.CENTER_Y - (currentSvgR + 3));
    }

    onRadisChange(strR) {
        const intR = parseInt(strR);
        this.redrawGraph(intR);
        this.drawPoints(intR);
    }

    $clickHandler(event) {
        const newPoint = this.svg.createSVGPoint();
        newPoint.x = event.clientX;
        newPoint.y = event.clientY;

        const transformedCoords = newPoint.matrixTransform(this.svg.getScreenCTM().inverse());
        const x = (transformedCoords.x - this.CENTER_X) / (this.MAX_SVG_R / 5);
        const y = -(transformedCoords.y - this.CENTER_Y) / (this.MAX_SVG_R / 5);

        this.formService.setX(x);
        this.formService.setY(y);
        this.formService.submitFormViaClick();
    }
}

class FormService {
    constructor(formId) {
        this.formId = formId
        this.onRadiusChangeObservers = []

        this.rSelect = document.getElementById(`${formId}:r-select`);
        this.rSelect.addEventListener('change', () => {
            const selectedValue = this.rSelect.value;
            this.$onRadiusChangeHandler(selectedValue);
        });
    }

    getData() {
        // Collecting data from form
        const form = document.getElementById(this.formId);
        const formData = new FormData(form);
        return {
            x: formData.get(`${this.formId}:x-input`),
            y: formData.get(`${this.formId}:y-input`),
            r: formData.get(`${this.formId}:r-select`)
        };
    }

    setX(x) {
        const roundedX = this.$roundToClosest(x, [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]);
        document.querySelector('select[id$=":x-input"]').value = roundedX;
    }

    setY(y) {
        document.querySelector('input[id$=":y-input"]').value = y
    }

    setTimezone(timezone) {
        document.querySelector('input[id$=":timezone-input"]').value = timezone
    }

    setData(x, y) {
        this.setX(x);
        this.setY(y);
    }

    validateForm(source) {
        try {
            const values = this.getData();
            Validator.validateAll(values.x, values.y, values.r);
            return true;
        } catch (e) {
            console.error(e);
            swal("Ошибка", e.message, "error")
            return false;
        }
    }

    submitFormViaClick() {
        document.getElementById(`${this.formId}:submit`).click();
    }


    $roundToClosest(value, valuesArray) {
        return valuesArray.reduce((prev, curr) =>
            Math.abs(curr - value) < Math.abs(prev - value) ? curr : prev
        );
    }

    $onRadiusChangeHandler(r) {
        this.onRadiusChangeObservers.forEach(observer => observer.onRadisChange(r));
    }

    addRadiusChangeObserver(observer) {
        this.onRadiusChangeObservers.push(observer)
    }
}

function onInit() {
    const formService = new FormService("form");
    const graphService = new GraphService("graph", formService);

    formService.addRadiusChangeObserver(graphService);

    // Setting up timezone
    const currentTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    formService.setTimezone(currentTimezone);

    // Setting up initial radius
    const INITIAL_R = 5;
    formService.rSelect.value = INITIAL_R;
    graphService.redrawGraph(INITIAL_R);

    // Initial draw points
    graphService.drawPoints(INITIAL_R);

    // MutationObserver для отслеживания появления новых точек
    const resultElement = document.getElementById("posints-data-obersver");
    if (resultElement) {
        const observer = new MutationObserver(function () {
            const currentData = formService.getData();
            const currentR = parseInt(currentData.r);
            graphService.drawPoints(currentR);
        });

        observer.observe(resultElement, {
            childList: true,
            subtree: true
        });
    }

    window.validateForm = () => {
        return formService.validateForm()
    };
}

document.addEventListener("DOMContentLoaded", onInit);
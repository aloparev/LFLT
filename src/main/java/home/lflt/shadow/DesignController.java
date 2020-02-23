package home.lflt.shadow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import home.lflt.shadow.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignController {

//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
//        List<Lot> lots = Arrays.asList(
//                new Lot("FLTO", "Flour Tortilla", Type.WRAP),
//                new Lot("COTO", "Corn Tortilla", Type.WRAP),
//                new Lot("GRBF", "Ground Beef", Type.PROTEIN),
//                new Lot("CARN", "Carnitas", Type.PROTEIN),
//                new Lot("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Lot("LETC", "Lettuce", Type.VEGGIES),
//                new Lot("CHED", "Cheddar", Type.CHEESE),
//                new Lot("JACK", "Monterrey Jack", Type.CHEESE),
//                new Lot("SLSA", "Salsa", Type.SAUCE),
//                new Lot("SRCR", "Sour Cream", Type.SAUCE)
//        );
//        Type[] types = Lot.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType(lots, type));
//        }
//    }

    //tag::showDesignForm[]
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());
        return "sDesign";
    }
//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesign[]
 */

    //tag::processDesignValidated[]
    @PostMapping
    public String processDesign(@ModelAttribute("design") Taco design, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "sDesign";
        }
        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
//end::processDesignValidated[]

    //tag::filterByType[]
    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
//end::filterByType[]
// tag::foot[]
}
// end::foot[]
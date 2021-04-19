package com.freshvotes.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {

    Logger log = LoggerFactory.getLogger(FeatureController.class);

    @Autowired
    private FeatureService featureService;

    @PostMapping("") // -> ends up mapping to /products/{productId}/features/
    public String createFeature(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        Feature feature = featureService.createFeature(productId, user); // little business logic in controller, too
                                                                         // much
        // shows bad design

        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("{featureId}")
    public String getFeature(@AuthenticationPrincipal User user, @PathVariable Long productId,
            @PathVariable Long featureId, ModelMap model) {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if (featureOpt.isPresent()) {
            Feature feature = featureOpt.get();
            model.put("feature", feature);
            model.put("comments", feature.getComments());
        }
        // TODO: handle the situation where we can't find a feature by featureId
        model.put("user", user); // user denotes the logged-in user
        return "feature";
    }

    @PostMapping("{featureId}")
    public String updateFeature(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long productId,
            @PathVariable Long featureId) {
        feature.setUser(user);
        feature = featureService.save(feature);
        String encodedProductName;
        try {
            encodedProductName = URLEncoder.encode(feature.getProduct().getName(), StandardCharsets.UTF_8.toString());
            System.out.println(encodedProductName);
        } catch (UnsupportedEncodingException e) {
            log.warn("Incorrect URL encoding of " + feature.getProduct().getName() + ", redirecting to dashboard.");
            return "redirect:/dashboard";
        }
        System.out.println(encodedProductName);
        return "redirect:/p/" + encodedProductName;
    }
}
